package com.example.lazypizza.feature.history.di

import com.example.lazypizza.feature.history.HistoryViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val historyModule = module {
    viewModelOf(::HistoryViewModel)
}