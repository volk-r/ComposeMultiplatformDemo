package com.example.composempdemo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import com.example.composempdemo.ui.ScrollableLazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composempdemo.dependencies.MyViewModel
import com.example.composempdemo.networking.InsultCensorClient
import com.example.composempdemo.util.NetworkError
import com.example.composempdemo.util.onError
import com.example.composempdemo.util.onSuccess
import composempdemo.app.shared.generated.resources.Res
import composempdemo.app.shared.generated.resources.battery
import composempdemo.app.shared.generated.resources.battery_level
import composempdemo.app.shared.generated.resources.battery_unavailable
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.UtcOffset
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Clock
import kotlin.time.Duration.Companion.hours

data class City(
    val name: String,
    val timeZOne: TimeZone,
)

@Composable
fun App(
    batteryManager: BatteryManager,
    client: InsultCensorClient,
    prefs: DataStore<Preferences>,
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
    val scope = rememberCoroutineScope()

    val counter by prefs
        .data
        .map {
            val counterKey = intPreferencesKey("counter")
            it[counterKey] ?: 0
        }
        .collectAsState(0)

    LaunchedEffect(batteryManager) {
        batteryManager.watchBatteryLevel { level ->
            batteryLevel = level
        }
    }

    val cities = remember {
        listOf(
            City("Moscow", TimeZone.of("Europe/Moscow")),
            City("Berlin", TimeZone.of("Europe/Berlin")),
            City("London", TimeZone.of("Europe/London")),
            City("New York", TimeZone.of("America/New_York")),
            City("Los Angeles", TimeZone.of("America/Los_Angeles")),
            City("Tokyo", TimeZone.of("Asia/Tokyo")),
            City("Sydney", TimeZone.of("Australia/Sydney")),
        )
    }
    var cityTimes by remember {
        mutableStateOf(
            listOf<Pair<City, LocalDateTime>>()
        )
    }
    LaunchedEffect(true) {
        while (true) {
            cityTimes = cities.map { city ->
                val now = Clock.System.now()
                city to now.toLocalDateTime(city.timeZOne)
            }
            delay(1000L)
        }
    }

    MaterialTheme {
        NavHost(
            navController = rememberNavController(),
            startDestination = "home",
            modifier = Modifier.fillMaxSize(),
        ) {
            composable(route = "home") {
                val viewModel = koinViewModel<MyViewModel>()

                ScrollableLazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .safeDrawingPadding(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                ) {
                    item {
                        Text(
                            text = viewModel.getHelloWorldString(),
                            modifier = Modifier.padding(bottom = 16.dp),
                        )
                    }
                    item {
                        Image(painterResource(Res.drawable.battery), contentDescription = null)
                    }
                    item {
                        Text(
                            modifier = Modifier.padding(top = 16.dp),
                            text = when {
                                batteryLevel < 0 -> stringResource(Res.string.battery_unavailable)
                                else -> stringResource(Res.string.battery_level, batteryLevel, "%")
                            }
                        )
                    }
                    item {
                        TextField(
                            value = uncensoredText,
                            onValueChange = { uncensoredText = it },
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .fillMaxWidth(),
                            placeholder = {
                                Text("Uncensored text!")
                            }
                        )
                    }
                    item {
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
                    }
                    censoredText?.let { text ->
                        item {
                            Text(text)
                        }
                    }
                    errorMessage?.let { error ->
                        item {
                            Text(
                                text = error.name,
                                color = Color.Red
                            )
                        }
                    }
                    item {
                        Text(
                            modifier = Modifier.padding(top = 16.dp),
                            text = counter.toString(),
                            fontSize = 50.sp
                        )
                    }
                    item {
                        Button(onClick = {
                            scope.launch {
                                prefs.edit { dataStore ->
                                    val counterKey = intPreferencesKey("counter")
                                    dataStore[counterKey] = counter + 1
                                }
                            }
                        }) {
                            Text("Increment")
                        }
                    }
                    item {
                        NativeButton(
                            onClick = {
                                scope.launch {
                                    prefs.edit { dataStore ->
                                        val counterKey = intPreferencesKey("counter")
                                        dataStore[counterKey] = counter + 1
                                    }
                                }
                            }
                        )
                    }


                    item {
                        Text(
                            modifier = Modifier.padding(top = 24.dp, bottom = 8.dp),
                            text = "World clocks",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    items(cityTimes) { (city, dateTime) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = city.name,
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Column(
                                horizontalAlignment = Alignment.End
                            ) {
                                Text(
                                    text = dateTime
                                        .format(
                                            LocalDateTime.Format {
                                                hour()
                                                char(':')
                                                minute()
                                                char(':')
                                                second()
                                            }
                                        ),
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.Light
                                )
                                Text(
                                    text = dateTime
                                        .format(
                                            LocalDateTime.Format {
                                                day()
                                                char('/')
                                                monthNumber()
                                                char('/')
                                                year()
                                            }
                                        ),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Light,
                                    textAlign = TextAlign.End
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
