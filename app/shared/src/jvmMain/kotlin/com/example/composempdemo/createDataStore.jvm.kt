package com.example.composempdemo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import java.io.File

fun createDataStore(): DataStore<Preferences> {
    return createDataStore {
        File(System.getProperty("java.io.tmpdir"), DATA_STORE_FILE_NAME).absolutePath
    }
}