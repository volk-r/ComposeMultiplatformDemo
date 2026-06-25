package com.example.composempdemo

import platform.UIKit.UIDevice
import kotlin.math.roundToInt

actual class BatteryManager {
    actual fun getBatteryLevel(): Int {
        UIDevice.currentDevice.batteryMonitoringEnabled = true
        val batteryLevel = UIDevice.currentDevice.batteryLevel
        return (batteryLevel * 100).roundToInt()
    }

    actual suspend fun loadBatteryLevel(): Int = getBatteryLevel()

    actual suspend fun watchBatteryLevel(onChange: (Int) -> Unit) {
        onChange(loadBatteryLevel())
    }
}

actual fun createBatteryManager(): BatteryManager = BatteryManager()
