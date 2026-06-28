package com.example.composempdemo

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun Counter() {
    var counter by remember { mutableIntStateOf(0) }
    Column {
        Text(counter.toString())
        Button(onClick = { counter++ }) {
            Text("Increment")
        }
    }
}