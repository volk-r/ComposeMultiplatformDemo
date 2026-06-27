package com.example.composempdemo.di

import android.app.Application
import org.koin.android.ext.koin.androidContext

fun initKoin(application: Application) {
    initKoin(config = {
        androidContext(application)
    })
}
