package com.darusc.mousedroid.networking.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothClass
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothHidDevice
import android.bluetooth.BluetoothHidDeviceAppQosSettings
import android.bluetooth.BluetoothHidDeviceAppSdpSettings
import android.bluetooth.BluetoothProfile
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import com.darusc.mousedroid.layouts.KeyboardLayoutUS
import com.darusc.mousedroid.mkinput.InputEvent
import com.darusc.mousedroid.networking.Connection
import com.darusc.mousedroid.networking.toHIDReport
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

@SuppressLint("MissingPermission") // For BLUETOOTH_CONNECT permission
@RequiresApi(Build.VERSION_CODES.P)
class BluetoothConnection(
    context: Context,
    private val listener: Listener
) : Connection() {

    private val bluetoothAdapter: BluetoothAdapter
    private val bluetoothAdapterWrapper = BluetoothAdapterWrapper.getInstance()!!

    private val layout = KeyboardLayoutUS()

    private var isClosing = false

    /**
     * The device that sends the reports
     */
    private var bluetoothHIDDevice: BluetoothHidDevice? = null

    /**
     * The device reports will be sent to
     */
    private var bluetoothHostDevice: BluetoothDevice? = null

    /**
     * SDP settings used for registering the app with the bluetooth HID device
     */
    private val sdp = BluetoothHidDeviceAppSdpSettings(
        "Mousedroid", "Android HID", "Mousedroid",
        BluetoothHidDevice.SUBCLASS1_COMBO,
        HID_REPORT_DESC
    )

    /**
     * SDP settings used for registering the app with the bluetooth HID device
     */
    private val qos = BluetoothHidDeviceAppQosSettings(
        BluetoothHidDeviceAppQosSettings.SERVICE_BEST_EFFORT,
        800, 9, 0, 11250, -1
    )

    private val callback: BluetoothHidDevice.Callback = object : BluetoothHidDevice.Callback() {
        override fun onConnectionStateChanged(device: BluetoothDevice?, state: Int) {
            super.onConnectionStateChanged(device, state)

            bluetoothHostDevice = if (state == BluetoothProfile.STATE_CONNECTED) device else null

            when (state) {
                BluetoothProfile.STATE_CONNECTING -> Log.d("Mousedroid", "Connecting...")
                BluetoothProfile.STATE_CONNECTED -> {
                    Log.d("Mousedroid", "Connected!")
                    listener.onConnected(Mode.BLUETOOTH)
                }

                BluetoothProfile.STATE_DISCONNECTING -> Log.d("Mousedroid", "Disconnecting...")
                BluetoothProfile.STATE_DISCONNECTED -> {
                    bluetoothHostDevice = null

                    // Only notify the listener if we aren't intentionally closing
                    if (!isClosing) {
                        Log.d("Mousedroid", "Link lost or target unreachable. Still registered and waiting.")
                        //listener.onDisconnected()
                    }
                }
            }
        }

        override fun onAppStatusChanged(pluggedDevice: BluetoothDevice?, registered: Boolean) {
            super.onAppStatusChanged(pluggedDevice, registered)

            if (registered) {
                // HID service ready
                Log.d("Mousedroid", "App registered successfully.")
                // Try to auto connect to a compatible host
                CoroutineScope(Dispatchers.Main).launch {
                    // Delay to allow the bluetooth stack to be ready for connecting,
                    // otherwise connection fails immediately
                    delay(2000)
                    val target = findCompatibleHost()
                    if (target != null && bluetoothHostDevice == null) {
                        Log.d("Mousedroid", "Paging target: ${target.name}")
                        val success = bluetoothHIDDevice?.connect(target)
                        if (success == false) {
                            Log.e("Mousedroid", "Paging failed. Device might be unreachable.")
                        }
                    }
                }
            } else {
                Log.e("Mousedroid", "Failed to register app. Check permissions or device compatibility.")
            }
        }
    }

    /**
     * Non blocking queue of reports to be sent over the bluetooth connection
     */
    private val reportChannel = Channel<Array<HIDReport>>(Channel.UNLIMITED)
    private val sendReportJob = CoroutineScope(Dispatchers.IO).launch {
        for (reports in reportChannel) {
            for (report in reports) {
                bluetoothHIDDevice?.sendReport(bluetoothHostDevice, report.id, report.bytes)
                if (report is KeyboardReport) {
                    delay(15)
                }
            }
        }
    }

    init {
        bluetoothAdapter = bluetoothAdapterWrapper.adapter

        bluetoothAdapter.getProfileProxy(context, object : BluetoothProfile.ServiceListener {
            override fun onServiceConnected(profile: Int, proxy: BluetoothProfile) {
                if (profile == BluetoothProfile.HID_DEVICE) {
                    bluetoothHIDDevice = proxy as BluetoothHidDevice
                    bluetoothHIDDevice!!.registerApp(
                        sdp,
                        null,
                        null,
                        Executors.newSingleThreadExecutor(),
                        callback
                    )
                }
            }

            override fun onServiceDisconnected(profile: Int) {
                bluetoothHIDDevice = null
            }
        }, BluetoothProfile.HID_DEVICE)
    }

    override fun send(event: InputEvent) {
        val reports = event.toHIDReport(layout)
        reportChannel.trySend(reports)
    }

    override fun close() {
        if(isClosing) {
            return
        }
        isClosing = true

        Log.d("Mousedroid", "Cleaning up Bluetooth connection...")

        sendReportJob.cancel()
        reportChannel.close()

        bluetoothHostDevice?.let {
            bluetoothHIDDevice?.disconnect(it)
        }

        bluetoothHIDDevice?.unregisterApp()
        bluetoothAdapter.closeProfileProxy(BluetoothProfile.HID_DEVICE, bluetoothHIDDevice)

        bluetoothHIDDevice = null
        bluetoothHostDevice = null
    }

    /**
     * Return a compatible host from the already paired devices
     * Prioritizes devices with class COMPUTER
     */
    private fun findCompatibleHost(): BluetoothDevice? {
        val pairedDevices = bluetoothAdapter.bondedDevices
        if (pairedDevices.isEmpty()) {
            return null
        }

        val computer = pairedDevices.firstOrNull { it.bluetoothClass.majorDeviceClass == BluetoothClass.Device.Major.COMPUTER }
        if (computer != null) {
            return computer
        }

        val tv = pairedDevices.firstOrNull { it.bluetoothClass.majorDeviceClass == BluetoothClass.Device.Major.AUDIO_VIDEO }
        if (tv != null) {
            return tv
        }

        return pairedDevices.firstOrNull {
            it.bluetoothClass.majorDeviceClass != BluetoothClass.Device.Major.WEARABLE
        }
    }
}