package com.example.composempdemo

import android.app.Application
import com.example.composempdemo.di.initKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(this)
    }
}
