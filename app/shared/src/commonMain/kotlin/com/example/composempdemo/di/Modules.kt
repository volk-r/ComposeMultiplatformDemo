package com.example.composempdemo.di

import com.example.composempdemo.dependencies.MyRepository
import com.example.composempdemo.dependencies.MyRepositoryImpl
import com.example.composempdemo.dependencies.MyViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    singleOf(::MyRepositoryImpl).bind<MyRepository>()
    viewModelOf(::MyViewModel)
}