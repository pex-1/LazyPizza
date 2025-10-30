package com.example.lazypizza.core.di

import com.example.lazypizza.core.data.UserDataDataStore
import com.example.lazypizza.core.data.dataStore
import com.example.lazypizza.core.data.repository.CartRepositoryImpl
import com.example.lazypizza.core.data.repository.ProductsRepositoryImpl
import com.example.lazypizza.core.domain.repository.CartRepository
import com.example.lazypizza.core.domain.repository.ProductsRepository
import com.example.lazypizza.core.domain.userdata.UserData
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataRepository = module {
    single<ProductsRepository> { ProductsRepositoryImpl(get()) }
    single<CartRepository> {
        CartRepositoryImpl(get())
    }
    single {
        FirebaseFirestore.getInstance()
    }

    single { androidContext().dataStore }

    single<UserData> { UserDataDataStore(get()) }
}