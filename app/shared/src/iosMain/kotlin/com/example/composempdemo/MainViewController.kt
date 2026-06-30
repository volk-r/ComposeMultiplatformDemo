package com.example.composempdemo

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.example.composempdemo.di.initKoin
import com.example.composempdemo.networking.InsultCensorClient
import com.example.composempdemo.networking.createHttpClient
import io.ktor.client.engine.darwin.Darwin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App(
        batteryManager = remember { BatteryManager() },
        client = remember { InsultCensorClient(createHttpClient(Darwin.create())) }
    )
}