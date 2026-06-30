package com.example.composempdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.composempdemo.di.platformModule
import com.example.composempdemo.di.sharedModule

import com.example.composempdemo.networking.InsultCensorClient
import com.example.composempdemo.networking.createHttpClient
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidContext
import org.koin.compose.KoinApplication
import org.koin.dsl.koinConfiguration

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App(
                batteryManager = remember { BatteryManager(applicationContext) },
                client = remember { InsultCensorClient(createHttpClient(OkHttp.create())) },
            )
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    val context = LocalContext.current
    KoinApplication(
        configuration = koinConfiguration {
            androidContext(context)
            modules(sharedModule, platformModule)
        }
    ) {
        App(
            batteryManager = remember(context) { BatteryManager(context) },
            client = remember { InsultCensorClient(createHttpClient(OkHttp.create())) },
        )
    }
}
