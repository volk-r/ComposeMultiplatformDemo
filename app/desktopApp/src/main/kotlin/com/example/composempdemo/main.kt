package com.example.composempdemo

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.composempdemo.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "ComposeMPDemo",
        ) {
            App(batteryManager = remember { BatteryManager() })
        }
    }
}