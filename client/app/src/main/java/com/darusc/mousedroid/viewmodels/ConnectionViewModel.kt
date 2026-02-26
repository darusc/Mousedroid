package com.darusc.mousedroid.viewmodels

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.annotation.IdRes
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.lifecycle.viewModelScope
import com.darusc.mousedroid.R
import com.darusc.mousedroid.getDeviceDetails
import com.darusc.mousedroid.networking.Connection
import com.darusc.mousedroid.networking.ConnectionManager
import com.darusc.mousedroid.networking.bluetooth.BluetoothAdapterWrapper
import com.darusc.mousedroid.networking.hasUsbConnection
import com.darusc.mousedroid.networking.hasWifiConnection
import kotlinx.coroutines.launch

class ConnectionViewModel :
    BaseViewModel<ConnectionViewModel.State, ConnectionViewModel.Event>(State.Idle),
    ConnectionManager.ConnectionStateCallback {

    sealed class State : BaseViewModel.State() {
        object Idle : State()
        data class Connecting(val message: String) : State()
        data class Connected(val connectionMode: Connection.Mode, val hostName: String) : State()
    }

    sealed class Event : BaseViewModel.Event() {
        data class Navigate(@IdRes val id: Int) : Event()
        object NavigateToInput : Event()
        object NavigateToMain : Event()
        object NavigateToDeviceList : Event()

        object EnableBluetooth : Event()

        data class ConnectionFailed(val connectionMode: Connection.Mode) : Event()
        data class ConnectionDisconnected(val connectionMode: Connection.Mode, val hostName: String) : Event()
    }

    private val connectionManager = ConnectionManager.getInstance(this)

    override fun onConnectionInitiated(mode: Connection.Mode) {
        if (state.value is State.Idle) {
            setState(State.Connecting(if (mode == Connection.Mode.BLUETOOTH) "Waiting for bluetooth connection..." else "Connecting..."))
        }
    }

    override fun onConnectionSuccessful(connectionMode: Connection.Mode, hostName: String) {
        setState(State.Connected(connectionMode, hostName))
        sendEvent(Event.NavigateToInput)
    }

    override fun onConnectionFailed(connectionMode: Connection.Mode) {
        setState(State.Idle)
        sendEvent(Event.ConnectionFailed(connectionMode))
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onDisconnected(connectionMode: Connection.Mode, hostName: String) {
        // Hardware link was lost (e.g host device's bluetooth was turned off)
        setState(State.Idle)
        sendEvent(Event.ConnectionDisconnected(connectionMode, hostName))
        sendEvent(Event.NavigateToMain)
    }

    /**
     * Start server mode with autodetect
     */
    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    fun startServerMode(context: Context) {
        // sendEvent(Event.NavigateToDeviceList)
        if (hasUsbConnection(context)) {
            // If app starts in server mode, check if there is a USB connection
            // If it is attempt to connect in USB mode (over ADB)
            connectionManager.connectUSB(6969, getDeviceDetails(context, Connection.Mode.USB))
        } else if (hasWifiConnection(context)) {
            // Otherwise if it has an active wifi connection, go to the
            // device list fragment to allow the user to choose the device to connect to
            sendEvent(Event.Navigate(R.id.action_main_to_devicelist))
        }
    }

    /**
     *  Start server mode over WIFI
     */
    fun startServerMode(address: String, port: Int, deviceDetails: String) {
        connectionManager.connectWIFI(address, port, deviceDetails)
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    @RequiresApi(Build.VERSION_CODES.P)
    fun startBluetoothMode(context: Context, afterEnableIntent: Boolean = false) {
        if (afterEnableIntent || BluetoothAdapterWrapper.getInstance()?.isEnabled!!) {
            connectionManager.connectBluetooth(context)
        } else {
            // Notify the fragment to start the bluetooth enable intent
            sendEvent(Event.EnableBluetooth)
        }
    }

    /**
     * Should be called only when the user requests a manual disconnect
     */
    @RequiresApi(Build.VERSION_CODES.P)
    fun disconnect() {
        viewModelScope.launch {
            connectionManager.disconnect()
            setState(State.Idle)
            sendEvent(Event.NavigateToMain)
        }
    }
}