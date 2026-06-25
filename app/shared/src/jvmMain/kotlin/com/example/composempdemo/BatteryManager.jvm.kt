package com.example.composempdemo

import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import oshi.SystemInfo
import kotlin.math.roundToInt

actual class BatteryManager {
    private val systemInfo = SystemInfo()

    actual fun getBatteryLevel(): Int {
        val powerSource = systemInfo.hardware.powerSources.firstOrNull()
        return powerSource?.remainingCapacityPercent?.times(100)?.roundToInt() ?: -1
    }

    actual suspend fun loadBatteryLevel(): Int = getBatteryLevel()

    actual suspend fun watchBatteryLevel(onChange: (Int) -> Unit) {
        while (currentCoroutineContext().isActive) {
            onChange(getBatteryLevel())
            delay(BATTERY_POLL_INTERVAL_MS)
        }
    }
}

actual fun createBatteryManager(): BatteryManager = BatteryManager()

private const val BATTERY_POLL_INTERVAL_MS = 2_000L
