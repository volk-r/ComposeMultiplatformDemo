package com.example.composempdemo

import kotlin.js.ExperimentalWasmJsInterop
import kotlin.js.JsModule

@OptIn(ExperimentalWasmJsInterop::class)
@JsModule("@js-joda/timezone")
external object JsJodaTimeZoneModule

@Suppress("unused")
private val jsJodaTimeZone = JsJodaTimeZoneModule
