package com.example.composempdemo

import js.promise.Promise
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.js.unsafeCast
import kotlin.math.roundToInt
import web.navigator.navigator

external interface BatteryStatus {
    val level: Double
}

private var cachedBatteryLevel: Int = -1

@Suppress("UNCHECKED_CAST")
actual class BatteryManager {
    actual fun getBatteryLevel(): Int = cachedBatteryLevel

    actual suspend fun loadBatteryLevel(): Int = suspendCoroutine { continuation ->
        val nav = navigator.unsafeCast<dynamic>()
        if (nav.getBattery == null || nav.getBattery == undefined) {
            continuation.resume(-1)
            return@suspendCoroutine
        }

        runCatching {
            // Must be called on navigator — detached reference causes "Illegal invocation"
            nav.getBattery().unsafeCast<Promise<BatteryStatus>>().then { battery ->
                val level = (battery.level * 100).roundToInt()
                cachedBatteryLevel = level
                continuation.resume(level)
                null
            }
        }.onFailure {
            continuation.resume(-1)
        }
    }

    actual suspend fun watchBatteryLevel(onChange: (Int) -> Unit) {
        onChange(loadBatteryLevel())
    }
}

actual fun createBatteryManager(): BatteryManager = BatteryManager()
