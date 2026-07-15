package com.example.composempdemo.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.composempdemo.createDataStore
import com.example.composempdemo.dependencies.DbClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

private fun platformDataStore(context: Context): DataStore<Preferences> =
    createDataStore(context)

actual val platformModule = module {
    singleOf(::DbClient)
    single<DataStore<Preferences>> { platformDataStore(get()) }
}