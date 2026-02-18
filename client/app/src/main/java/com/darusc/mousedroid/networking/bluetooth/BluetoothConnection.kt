package com.darusc.mousedroid.networking.bluetooth

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothHidDevice
import android.bluetooth.BluetoothHidDeviceAppQosSettings
import android.bluetooth.BluetoothHidDeviceAppSdpSettings
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
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

@RequiresApi(Build.VERSION_CODES.P)
class BluetoothConnection(
    context: Context,
    private val listener: Connection.Listener
) : Connection() {

    private val applicationContext = context.applicationContext

    private val bluetoothAdapter: BluetoothAdapter
    private var bluetoothManager: BluetoothManager? = null

    private val layout = KeyboardLayoutUS()

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
        @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
        override fun onConnectionStateChanged(device: BluetoothDevice?, state: Int) {
            super.onConnectionStateChanged(device, state)

            bluetoothHostDevice = if (state == BluetoothProfile.STATE_CONNECTED) {
                device
            } else {
                null
            }

            when (state) {
                BluetoothProfile.STATE_CONNECTING -> Log.d("Mousedroid", "Connecting...")
                BluetoothProfile.STATE_CONNECTED -> {
                    Log.d("Mousedroid", "Connected!")
                    listener.onConnected(Mode.BLUETOOTH)
                }

                BluetoothProfile.STATE_DISCONNECTING -> Log.d("Mousedroid", "Disconnecting...")
                BluetoothProfile.STATE_DISCONNECTED -> Log.d("Mousedroid", "Disconnected!")
            }
        }

        override fun onAppStatusChanged(pluggedDevice: BluetoothDevice?, registered: Boolean) {
            super.onAppStatusChanged(pluggedDevice, registered)

            if (registered) {
                // The HID service is ready, make the phone visible.
                Log.d("HID", "App registered successfully. Requesting discoverability...");
                makeDeviceDiscoverable();
            } else {
                Log.e("HID", "Failed to register app. Check permissions or device compatibility.");
            }
        }
    }

    /**
     * Non blocking queue of reports to be sent over the bluetooth connection
     */
    private val reportChannel = Channel<Array<HIDReport>>(Channel.UNLIMITED)
    @SuppressLint("MissingPermission")
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // BluetoothAdapter.getDefaultAdapter() is deprecated on version >= 31
            bluetoothManager = applicationContext.getSystemService(BluetoothManager::class.java)
            bluetoothAdapter = bluetoothManager!!.adapter
        } else {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        }

        bluetoothAdapter.getProfileProxy(context, object : BluetoothProfile.ServiceListener {
            @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
            override fun onServiceConnected(profile: Int, proxy: BluetoothProfile) {
                if (profile == BluetoothProfile.HID_DEVICE) {
                    bluetoothHIDDevice = proxy as BluetoothHidDevice
                    bluetoothHIDDevice!!.registerApp(
                        sdp,
                        qos,
                        qos,
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

    private fun makeDeviceDiscoverable() {
        val discoverableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
        // Make visible for 300 seconds (5 minutes)
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300)
        discoverableIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(applicationContext, discoverableIntent, null)
    }

    override fun send(event: InputEvent) {
        val reports = event.toHIDReport(layout)
        reportChannel.offer(reports)
    }

    override fun close() {
        sendReportJob.cancel()
        reportChannel.close()
    }
}