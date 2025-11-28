package com.example.lazypizza.feature.authentication.di

import com.example.lazypizza.feature.authentication.AuthenticationViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authenticationModule = module {
    viewModelOf(::AuthenticationViewModel)
}