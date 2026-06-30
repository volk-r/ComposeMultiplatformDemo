package com.example.composempdemo

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.composempdemo.di.initKoin
import com.example.composempdemo.networking.InsultCensorClient
import com.example.composempdemo.networking.createHttpClient
import io.ktor.client.engine.okhttp.OkHttp

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "ComposeMPDemo",
        ) {
            App(
                batteryManager = remember { BatteryManager() },
                client = remember { InsultCensorClient(createHttpClient(OkHttp.create())) },
            )
        }
    }
}