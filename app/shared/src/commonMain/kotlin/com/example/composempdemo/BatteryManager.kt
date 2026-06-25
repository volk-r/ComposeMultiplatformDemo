package com.example.composempdemo

expect class BatteryManager {
    fun getBatteryLevel(): Int
    suspend fun loadBatteryLevel(): Int
    suspend fun watchBatteryLevel(onChange: (Int) -> Unit)
}

expect fun createBatteryManager(): BatteryManager
