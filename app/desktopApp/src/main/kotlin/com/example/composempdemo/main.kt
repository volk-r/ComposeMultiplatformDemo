package com.example.composempdemo

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.composempdemo.di.initKoin
import com.example.composempdemo.networking.InsultCensorClient
import com.example.composempdemo.networking.createHttpClient
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.compose.koinInject

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "ComposeMPDemo",
        ) {
            val dataStore = koinInject<DataStore<Preferences>>()
            App(
                batteryManager = remember { BatteryManager() },
                client = remember { InsultCensorClient(createHttpClient(OkHttp.create())) },
                prefs = remember { dataStore },
            )
        }
    }
}