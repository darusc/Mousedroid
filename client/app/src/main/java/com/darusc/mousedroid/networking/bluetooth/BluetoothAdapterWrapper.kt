package com.darusc.mousedroid.networking.bluetooth

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresPermission

/**
 * Wrapper class around BluetoothAdapter to provide unified access
 * to the android's BluetoothAdapter
 */
class BluetoothAdapterWrapper private constructor(context: Context) {

    companion object {
        @Volatile
        private var instance: BluetoothAdapterWrapper? = null

        fun getInstance(): BluetoothAdapterWrapper? {
            synchronized(this) {
                return instance
            }
        }

        fun initialize(context: Context) {
            instance = BluetoothAdapterWrapper(context)
        }
    }

    private val _adapter: BluetoothAdapter
    val adapter: BluetoothAdapter
        get() = _adapter

    val isEnabled: Boolean
        get() = _adapter.isEnabled

    val pairedDevices: Set<BluetoothDevice>
        @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
        get() = _adapter.bondedDevices

    init {
        _adapter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // BluetoothAdapter.getDefaultAdapter() is deprecated on version >= 31
            val bluetoothManager = context.getSystemService(BluetoothManager::class.java)
            bluetoothManager!!.adapter
        } else {
            BluetoothAdapter.getDefaultAdapter()
        }
    }
}