package com.example.composempdemo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composempdemo.dependencies.DbClient
import com.example.composempdemo.dependencies.MyViewModel
import composempdemo.app.shared.generated.resources.Res
import composempdemo.app.shared.generated.resources.battery
import composempdemo.app.shared.generated.resources.battery_level
import composempdemo.app.shared.generated.resources.battery_unavailable
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App(batteryManager: BatteryManager) {
    var batteryLevel by remember { mutableIntStateOf(batteryManager.getBatteryLevel()) }

    LaunchedEffect(batteryManager) {
        batteryManager.watchBatteryLevel { level ->
            batteryLevel = level
        }
    }

    MaterialTheme {
        NavHost(
            navController = rememberNavController(),
            startDestination = "home",
        ) {
            composable(route = "home") {
                val viewModel = koinViewModel<MyViewModel>()

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = viewModel.getHelloWorldString(),
                        modifier = Modifier.padding(bottom = 16.dp),
                    )
                    Image(painterResource(Res.drawable.battery), contentDescription = null)
                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = when {
                            batteryLevel < 0 -> stringResource(Res.string.battery_unavailable)
                            else -> stringResource(Res.string.battery_level, batteryLevel, "%")
                        }
                    )
                }
            }
        }
    }
}
