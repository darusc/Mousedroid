package com.darusc.mousedroid

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager

class BatteryMonitor {

    companion object {
        @Volatile
        private var instance: BatteryMonitor? = null

        fun getInstance(): BatteryMonitor {
            synchronized(this) {
                return instance ?: BatteryMonitor().also { instance = it }
            }
        }
    }

    interface Listener {
        fun onBatteryPercentChanged(percentage: Int)
    }

    private var lastReportedLevel = 0
    private val listeners: MutableSet<Listener> = HashSet()

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            if (level != -1 && scale != -1) {
                val pct = (level * 100 / scale.toFloat()).toInt()
                if (pct != lastReportedLevel) {
                    listeners.forEach { it.onBatteryPercentChanged(pct) }
                    lastReportedLevel = pct
                }
            }
        }
    }

    fun addListener(listener: Listener) {
        listeners.add(listener)
    }

    fun removeListener(listener: Listener) {
        listeners.remove(listener)
    }

    fun start(context: Context) {
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        context.registerReceiver(receiver, filter)
    }

    fun stop(context: Context) {
        try {
            context.unregisterReceiver(receiver)
        } catch (_: Exception) { }
    }
}