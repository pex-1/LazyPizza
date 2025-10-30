package com.example.lazypizza.feature.cart.di

import com.example.lazypizza.core.data.repository.CartRepositoryImpl
import com.example.lazypizza.core.domain.repository.CartRepository
import com.example.lazypizza.feature.cart.CartViewModel
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val cartDataModule = module {
    single { FirebaseFirestore.getInstance() }

    singleOf(::CartRepositoryImpl) { bind<CartRepository>() }
    viewModelOf(::CartViewModel)
}