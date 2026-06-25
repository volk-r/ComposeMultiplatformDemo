package com.example.composempdemo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun App(batteryManager: BatteryManager) {
    var batteryLevel by remember { mutableIntStateOf(batteryManager.getBatteryLevel()) }

    LaunchedEffect(batteryManager) {
        batteryManager.watchBatteryLevel { level ->
            batteryLevel = level
        }
    }

    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = when {
                    batteryLevel < 0 -> "Battery level unavailable"
                    else -> "The current battery level is $batteryLevel%"
                },
            )
        }
    }
}
