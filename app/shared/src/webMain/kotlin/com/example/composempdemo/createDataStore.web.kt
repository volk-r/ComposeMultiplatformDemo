package com.example.composempdemo

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.WebLocalStorage
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferencesSerializer

fun createDataStore(): DataStore<Preferences> {
    return DataStoreFactory.create(
        storage = WebLocalStorage(
            serializer = PreferencesSerializer,
            name = DATA_STORE_FILE_NAME,
        ),
    )
}