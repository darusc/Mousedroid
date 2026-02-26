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
import com.darusc.mousedroid.BatteryMonitor
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
    private var connectionEstablished = false
    private var connectionAttempts = 0
    private val MAX_CONNECTION_ATTEMPTS = 2

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
     * QOS settings used for registering the app with the bluetooth HID device
     */
    private val qos = BluetoothHidDeviceAppQosSettings(
        BluetoothHidDeviceAppQosSettings.SERVICE_BEST_EFFORT,
        800, 9, 0, 11250, -1
    )

    private val callback: BluetoothHidDevice.Callback = object : BluetoothHidDevice.Callback() {
        override fun onConnectionStateChanged(device: BluetoothDevice?, state: Int) {
            super.onConnectionStateChanged(device, state)

            val hostname = bluetoothHostDevice?.name ?: "Unknown"
            bluetoothHostDevice = if (state == BluetoothProfile.STATE_CONNECTED) device else null

            when (state) {
                BluetoothProfile.STATE_CONNECTING -> Log.d("Mousedroid", "Connecting...")

                BluetoothProfile.STATE_CONNECTED -> {
                    Log.d("Mousedroid", "Connected!")
                    connectionAttempts = 0
                    connectionEstablished = true
                    listener.onConnected(Mode.BLUETOOTH, bluetoothHostDevice?.name ?: "Unknown device")

                    // Send a battery report after connecting
                    CoroutineScope(Dispatchers.IO).launch {
                        delay(3000)
                        if (bluetoothHostDevice != null) {
                            val level = BatteryMonitor.getBatteryLevel(context)
                            send(InputEvent.BatteryEvent(level))
                        }
                    }
                }

                BluetoothProfile.STATE_DISCONNECTING -> Log.d("Mousedroid", "Disconnecting...")

                BluetoothProfile.STATE_DISCONNECTED -> {
                    bluetoothHostDevice = null

                    if (isClosing) {
                        cleanupProxy()
                    } else {
                        if (connectionEstablished) {
                            // Host turned off or went out of range after the connection
                            // was established. Instant disconnect
                            Log.d("Mousedroid", "Active session lost. Disconnecting instantly.")
                            listener.onDisconnected(Mode.BLUETOOTH, hostname)
                            connectionEstablished = false
                        } else if (connectionAttempts < MAX_CONNECTION_ATTEMPTS) {
                            // If connection was rejected instantly retry silently
                            connectionAttempts++
                            Log.d("Mousedroid", "Silent retry. Attempt: $connectionAttempts/2")

                            // Try again silently without bothering the UI
                            CoroutineScope(Dispatchers.Main).launch {
                                delay(2000)
                                if (!isClosing && bluetoothHostDevice == null) {
                                    val target = findCompatibleHost()
                                    target?.let { bluetoothHIDDevice?.connect(it) }
                                }
                            }
                        } else {
                            // Connection still failed after all retry attempts
                            Log.d("Mousedroid", "Connection failed after 3 attempts")
                            connectionAttempts = 0
                            listener.onConnectionFailed(Mode.BLUETOOTH)
                        }
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
                Log.e(
                    "Mousedroid",
                    "Failed to register app. Check permissions or device compatibility."
                )
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
        if (isClosing) {
            return
        }
        isClosing = true

        Log.d("Mousedroid", "Cleaning up Bluetooth connection...")

        sendReportJob.cancel()
        reportChannel.close()

        if (bluetoothHostDevice != null && bluetoothHIDDevice != null) {
            val disconnected = bluetoothHIDDevice?.disconnect(bluetoothHostDevice)
            if (disconnected == false) {
                cleanupProxy()
            }
        } else {
            cleanupProxy()
        }
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

        val computer =
            pairedDevices.firstOrNull { it.bluetoothClass.majorDeviceClass == BluetoothClass.Device.Major.COMPUTER }
        if (computer != null) {
            return computer
        }

        val tv =
            pairedDevices.firstOrNull { it.bluetoothClass.majorDeviceClass == BluetoothClass.Device.Major.AUDIO_VIDEO }
        if (tv != null) {
            return tv
        }

        return pairedDevices.firstOrNull {
            it.bluetoothClass.majorDeviceClass != BluetoothClass.Device.Major.WEARABLE
        }
    }

    /**
     * Unregisters the HID app and closes the HID_DEVICE profile proxy
     */
    private fun cleanupProxy() {
        bluetoothHIDDevice?.unregisterApp()
        bluetoothAdapter.closeProfileProxy(BluetoothProfile.HID_DEVICE, bluetoothHIDDevice)
        bluetoothHIDDevice = null
    }
}