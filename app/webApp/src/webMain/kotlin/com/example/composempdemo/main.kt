package com.example.composempdemo

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.composempdemo.di.initKoin
import com.example.composempdemo.networking.InsultCensorClient
import com.example.composempdemo.networking.createHttpClient
import io.ktor.client.engine.js.Js
import org.koin.compose.koinInject

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    initKoin()
    ComposeViewport {
        val dataStore = koinInject<DataStore<Preferences>>()
        App(
            batteryManager = createBatteryManager(),
            client = InsultCensorClient(createHttpClient(Js.create())),
            prefs = dataStore,
        )
    }
}