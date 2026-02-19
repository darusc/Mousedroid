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

class ConnectionViewModel :
    BaseViewModel<ConnectionViewModel.State, ConnectionViewModel.Event>(State.Idle),
    ConnectionManager.ConnectionStateCallback {

    sealed class State : BaseViewModel.State() {
        object Idle : State()
        object Connecting : State()
        object Connected : State()
    }

    sealed class Event : BaseViewModel.Event() {
        data class Navigate(@IdRes val id: Int) : Event()
        object NavigateToInput: Event()
        object NavigateToMain: Event()
        object ConnectionFailed: Event()
        object ConnectionDisconnected: Event()
    }

    private val connectionManager = ConnectionManager.getInstance(this)

    @RequiresApi(Build.VERSION_CODES.P)
    fun connectInBluetoothMode(context: Context) {
        connectionManager.connectBluetooth(context)
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    fun connectInUSBMode(context: Context) {
        connectionManager.connectUSB(6969, getDeviceDetails(context, Connection.Mode.USB))
    }

    fun connectInWifiMode(address: String, port: Int, deviceDetails: String) {
        connectionManager.connectWIFI(address, port, deviceDetails)
    }

    override fun onConnectionInitiated() {
        if (state.value is State.Idle) {
            setState(State.Connecting)
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