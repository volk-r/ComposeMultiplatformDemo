package com.example.composempdemo

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.example.composempdemo.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App(batteryManager = remember { BatteryManager() })
}