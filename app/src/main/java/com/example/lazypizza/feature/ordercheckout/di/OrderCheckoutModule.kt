package com.example.lazypizza.feature.ordercheckout.di

import com.example.lazypizza.feature.ordercheckout.OrderCheckoutViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val orderCheckoutModule = module {
    viewModelOf(::OrderCheckoutViewModel)
}