package com.example.composempdemo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composempdemo.dependencies.DbClient
import com.example.composempdemo.dependencies.MyViewModel
import com.example.composempdemo.networking.InsultCensorClient
import com.example.composempdemo.util.NetworkError
import com.example.composempdemo.util.onError
import com.example.composempdemo.util.onSuccess
import composempdemo.app.shared.generated.resources.Res
import composempdemo.app.shared.generated.resources.battery
import composempdemo.app.shared.generated.resources.battery_level
import composempdemo.app.shared.generated.resources.battery_unavailable
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App(
    batteryManager: BatteryManager,
    client: InsultCensorClient
) {
    var batteryLevel by remember { mutableIntStateOf(batteryManager.getBatteryLevel()) }

    var censoredText by remember {
        mutableStateOf<String?>(null)
    }
    var uncensoredText by remember {
        mutableStateOf("")
    }
    var isLoading by remember {
        mutableStateOf(false)
    }
    var errorMessage by remember {
        mutableStateOf<NetworkError?>(null)
    }
    var scope = rememberCoroutineScope()

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

                    TextField(
                        value = uncensoredText,
                        onValueChange = { uncensoredText = it },
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 16.dp)
                            .fillMaxWidth(),
                        placeholder = {
                            Text("Uncensored text!")
                        }
                    )
                    Button(onClick = {
                        scope.launch {
                            isLoading = true
                            errorMessage = null

                            client.censorWords(uncensoredText)
                                .onSuccess {
                                    censoredText = it
                                }
                                .onError {
                                    errorMessage = it
                                }
                            isLoading = false
                        }
                    }) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(15.dp),
                                strokeWidth = 1.dp,
                                color = Color.White
                            )
                        } else {
                            Text("Censor!")
                        }
                    }
                    censoredText?.let {
                        Text(it)
                    }
                    errorMessage?.let {
                        Text(
                            text = it.name,
                            color = Color.Red
                        )
                    }
                }
            }
        }
    }
}
