package com.hinalin.mousho

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.hinalin.mousho.data.model.OverheatEvent
import java.time.LocalDateTime

class BatteryTemperatureMonitor(context: Context) {

    var isOverheated: Boolean = false
        private set

    var currentTemperature: Float = 0f
        private set

    var onTemperatureChanged: ((Float) -> Unit)? = null

    var onOverheatedChanged: ((Boolean, Float) -> Unit)? = null

    private val overheatEvents = mutableListOf<OverheatEvent>()

    @RequiresApi(Build.VERSION_CODES.O)
    fun getOverheatEvents(): List<OverheatEvent> {
        val twentyFourHoursAgo = LocalDateTime.now().minusHours(24)
        return overheatEvents.filter { it.timestamp.isAfter(twentyFourHoursAgo) }
    }

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val batteryTemperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) / 10.0f
            val wasOverheated = isOverheated
            isOverheated = batteryTemperature > 40.0f
            currentTemperature = batteryTemperature

            onTemperatureChanged?.invoke(currentTemperature)

            if (isOverheated && !wasOverheated) {
                overheatEvents.add(OverheatEvent(LocalDateTime.now(), currentTemperature))
                onOverheatedChanged?.invoke(isOverheated, currentTemperature)
            }

            Log.d("BatteryTemperatureMonitor", "Battery temperature: $currentTemperature°C, Overheated: $isOverheated")
        }
    }

    init {
        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        context.registerReceiver(broadcastReceiver, intentFilter)
    }

    fun stopMonitoring(context: Context) {
        context.unregisterReceiver(broadcastReceiver)
    }
}
