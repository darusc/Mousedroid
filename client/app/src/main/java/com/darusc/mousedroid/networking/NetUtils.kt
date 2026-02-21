package com.darusc.mousedroid.networking

import android.Manifest
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.annotation.RequiresPermission

fun hasUsbConnection(context: Context): Boolean {
    val intent = context.registerReceiver(
        null,
        IntentFilter("android.hardware.usb.action.USB_STATE")
    )
    return intent?.getBooleanExtra("connected", false) == true
}

@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
fun hasWifiConnection(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
}