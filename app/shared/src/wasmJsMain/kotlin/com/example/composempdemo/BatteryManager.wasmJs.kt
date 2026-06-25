@file:OptIn(ExperimentalWasmJsInterop::class)

package com.example.composempdemo

import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.js.ExperimentalWasmJsInterop

@JsFun(
    """(callback) => {
        const nav = globalThis.navigator;
        if (nav && nav.getBattery) {
            nav.getBattery().then((battery) => callback(Math.round(battery.level * 100)));
        } else {
            callback(-1);
        }
    }""",
)
external fun requestBatteryLevel(callback: (Int) -> Unit)

private var cachedBatteryLevel: Int = -1

actual class BatteryManager {
    actual fun getBatteryLevel(): Int = cachedBatteryLevel

    actual suspend fun loadBatteryLevel(): Int = suspendCoroutine { continuation ->
        requestBatteryLevel { level ->
            cachedBatteryLevel = level
            continuation.resume(level)
        }
    }

    actual suspend fun watchBatteryLevel(onChange: (Int) -> Unit) {
        onChange(loadBatteryLevel())
    }
}

actual fun createBatteryManager(): BatteryManager = BatteryManager()
