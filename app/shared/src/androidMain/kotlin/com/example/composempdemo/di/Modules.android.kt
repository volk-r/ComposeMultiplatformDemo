package com.example.composempdemo.di

import com.example.composempdemo.dependencies.DbClient
import com.example.composempdemo.dependencies.MyViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

actual val platformModule = module {
    singleOf(::DbClient)
}