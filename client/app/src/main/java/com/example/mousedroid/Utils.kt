package com.example.mousedroid

import android.Manifest
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Context.BLUETOOTH_SERVICE
import android.content.IntentFilter
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresPermission
import com.example.mousedroid.networking.ConnectionManager.Mode

fun getConnectionMode(context: Context): Mode {
    val intent = context.registerReceiver(null, IntentFilter("android.hardware.usb.action.USB_STATE"))
    return if(intent?.getBooleanExtra("connected", false) == true) Mode.USB else Mode.WIFI
}

@RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
fun getDeviceDetails(context: Context, connectionMode: Mode): String {
    val manufacturer = Build.MANUFACTURER
    val model = Build.MODEL

    val deviceName = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S_V2) {
        (context.getSystemService(BLUETOOTH_SERVICE) as BluetoothManager).adapter.name
    } else {
        Settings.Secure.getString(context.contentResolver, "bluetooth_name")
    }

    val deviceDetails = "$manufacturer/$deviceName/$model/$connectionMode"
    return deviceDetails
}