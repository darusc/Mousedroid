package com.darusc.mousedroid.viewmodels

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.annotation.IdRes
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.darusc.mousedroid.R
import com.darusc.mousedroid.getDeviceDetails
import com.darusc.mousedroid.networking.Connection
import com.darusc.mousedroid.networking.ConnectionManager
import com.darusc.mousedroid.networking.hasUsbConnection
import com.darusc.mousedroid.networking.hasWifiConnection

class ConnectionViewModel :
    BaseViewModel<ConnectionViewModel.State, ConnectionViewModel.Event>(State.Idle),
    ConnectionManager.ConnectionStateCallback {

    sealed class State : BaseViewModel.State() {
        object Idle : State()
        data class Connecting(val message: String) : State()
        object Connected : State()
    }

    sealed class Event : BaseViewModel.Event() {
        data class Navigate(@IdRes val id: Int) : Event()
        object NavigateToInput : Event()
        object NavigateToMain : Event()
        object NavigateToDeviceList: Event()
        object ConnectionFailed : Event()
        object ConnectionDisconnected : Event()
    }

    private val connectionManager = ConnectionManager.getInstance(this)

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

    @RequiresApi(Build.VERSION_CODES.P)
    fun startBluetoothMode(context: Context) {
        connectionManager.connectBluetooth(context)
    }

    /**
     *  Should only be called by the DeviceList fragment after a device was selected
     */
    fun connectInWifiMode(address: String, port: Int, deviceDetails: String) {
        connectionManager.connectWIFI(address, port, deviceDetails)
    }

    override fun onConnectionInitiated(mode: Connection.Mode) {
        if (state.value is State.Idle) {
            setState(State.Connecting(if (mode == Connection.Mode.BLUETOOTH) "Waiting for bluetooth connection..." else "Connecting..."))
        }
    }

    override fun onConnectionSuccessful(connectionMode: Connection.Mode) {
        setState(State.Connected)
        sendEvent(Event.NavigateToInput)
    }

    override fun onConnectionFailed(connectionMode: Connection.Mode) {
        setState(State.Idle)
        sendEvent(Event.ConnectionFailed)
    }

    override fun onDisconnected() {
        setState(State.Idle)
        sendEvent(Event.ConnectionDisconnected)
        sendEvent(Event.NavigateToMain)
    }
}