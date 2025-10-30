package com.example.lazypizza.feature.maincatalog.di

import com.example.lazypizza.feature.maincatalog.MainCatalogViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val mainCatalogModule = module {
    viewModelOf(::MainCatalogViewModel)
}