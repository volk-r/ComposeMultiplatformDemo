package com.example.composempdemo.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.composempdemo.createDataStore
import com.example.composempdemo.dependencies.DbClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

private fun platformDataStore(): DataStore<Preferences> = createDataStore()

actual val platformModule = module {
    singleOf(::DbClient)
    single<DataStore<Preferences>> { platformDataStore() }
}