package com.darusc.mousedroid.viewmodels

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.darusc.mousedroid.getDeviceDetails
import com.darusc.mousedroid.networking.Connection
import com.darusc.mousedroid.networking.ConnectionManager

/**
 * @param devices The list of bluetooth devices
 * @param sharedPreferences Shared preferences for saved WIFI devices
 */
class DeviceListViewModel(
    private val mode: Connection.Mode,
    private val devices: List<Pair<String, String>>?,
    private val sharedPreferences: SharedPreferences?
): BaseViewModel<DeviceListViewModel.State, DeviceListViewModel.Event>(State(emptyList())) {

    sealed class Event: BaseViewModel.Event()
    data class State(val devices: List<Pair<String, String>>): BaseViewModel.State()

    private val connectionManager = ConnectionManager.getInstance()

    class Factory: ViewModelProvider.Factory {

        private val devices: List<Pair<String, String>>?
        private val sharedPreferences: SharedPreferences?

        /**
         * Create the viewmodel corresponding for bluetooth mode.
         * @param devices The list of paired bluetooth devices
         */
        @SuppressLint("MissingPermission")
        constructor(devices: Set<BluetoothDevice>) {
            this.devices = devices.map {
                Pair(it.name?: "Unknown", it.address)
            }
            this.sharedPreferences = null
        }

        /**
         * Create the viewmodel corresponding for wifi mode
         * @param sharedPreferences The shared preferences containing the stored WIFI devices
         */
        constructor(sharedPreferences: SharedPreferences?) {
            this.devices = null
            this.sharedPreferences = sharedPreferences
        }

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(DeviceListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return if(this.devices != null) {
                    DeviceListViewModel(Connection.Mode.BLUETOOTH, devices, null) as T
                } else {
                    DeviceListViewModel(Connection.Mode.WIFI, null, sharedPreferences) as T
                }
            }
            throw IllegalArgumentException("Unknown viewmodel class")
        }
    }

    init {
        updateState()
    }

    fun add(name: String, address: String) {
        sharedPreferences?.edit { putString(name, address) }
        updateState()
    }

    fun remove(name: String) {
        sharedPreferences?.edit { remove(name) }
        updateState()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    fun onDeviceClick(context: Context, name: String, address: String) {
        if(mode == Connection.Mode.WIFI) {
            val details = getDeviceDetails(context, Connection.Mode.WIFI)
            connectionManager.connectWIFI(address, 6969, details)
        } else {
            connectionManager.connectBluetooth(address)
        }
    }

    private fun updateState() {
        if(mode == Connection.Mode.BLUETOOTH) {
            setState(State(devices!!))
        } else {
            val devices = mutableListOf<Pair<String, String>>()
            sharedPreferences!!.all.let {
                for((name, address) in it) {
                    devices.add(Pair(name, address as String))
                }
            }
            setState(State(devices))
        }
    }
}