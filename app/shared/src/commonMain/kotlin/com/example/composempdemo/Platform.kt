package com.example.composempdemo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform