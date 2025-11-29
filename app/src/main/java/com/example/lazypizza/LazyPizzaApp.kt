package com.example.lazypizza

import android.app.Application
import android.content.pm.ApplicationInfo
import com.example.lazypizza.core.di.dataRepository
import com.example.lazypizza.di.appModule
import com.example.lazypizza.feature.authentication.di.authenticationModule
import com.example.lazypizza.feature.cart.di.cartDataModule
import com.example.lazypizza.feature.history.di.historyModule
import com.example.lazypizza.feature.maincatalog.di.mainCatalogModule
import com.example.lazypizza.feature.ordercheckout.di.orderCheckoutModule
import com.example.lazypizza.feature.productdetails.di.productDetailsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import timber.log.Timber

class LazyPizzaApp : Application() {
    override fun onCreate() {
        super.onCreate()

        if ((applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@LazyPizzaApp)
            androidLogger()
            modules(
                appModule,
                mainCatalogModule,
                productDetailsModule,
                dataRepository,
                cartDataModule,
                historyModule,
                authenticationModule,
                orderCheckoutModule
            )
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}