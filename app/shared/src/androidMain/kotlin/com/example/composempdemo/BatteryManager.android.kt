package com.example.composempdemo

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager as AndroidBatteryManager
import kotlin.math.roundToInt

actual class BatteryManager(
    private val context: Context,
) {
    actual fun getBatteryLevel(): Int {
        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val batteryStatus = context.registerReceiver(null, intentFilter)
        val level = batteryStatus?.getIntExtra(AndroidBatteryManager.EXTRA_LEVEL, -1) ?: -1
        val scale = batteryStatus?.getIntExtra(AndroidBatteryManager.EXTRA_SCALE, -1) ?: -1
        return (level / scale.toFloat() * 100).roundToInt()
    }

    actual suspend fun loadBatteryLevel(): Int = getBatteryLevel()

    actual suspend fun watchBatteryLevel(onChange: (Int) -> Unit) {
        onChange(loadBatteryLevel())
    }
}

actual fun createBatteryManager(): BatteryManager =
    error("Use BatteryManager(context) on Android")
