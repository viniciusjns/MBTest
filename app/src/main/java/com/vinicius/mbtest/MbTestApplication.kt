package com.vinicius.mbtest

import android.app.Application
import com.vinicius.mbtest.di.networkModule
import com.vinicius.mbtest.features.exchanges.impl.di.exchangeModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MbTestApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(level = Level.DEBUG)
            androidContext(this@MbTestApplication)
            modules(networkModule, exchangeModule)
        }
    }
}