package com.example.lazypizza.feature.productdetails.di

import com.example.lazypizza.feature.productdetails.ProductDetailViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val productDetailsModule = module {
    viewModelOf(::ProductDetailViewModel)
}