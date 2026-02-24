package com.darusc.mousedroid.networking.bluetooth

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.os.Build

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

    init {
        _adapter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // BluetoothAdapter.getDefaultAdapter() is deprecated on version >= 31
            val bluetoothAdapterWrapper = context.getSystemService(BluetoothAdapterWrapper::class.java)
            bluetoothAdapterWrapper!!.adapter
        } else {
            BluetoothAdapter.getDefaultAdapter()
        }
    }
}