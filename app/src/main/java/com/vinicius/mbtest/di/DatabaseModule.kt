package com.vinicius.mbtest.di

import androidx.room.Room
import com.vinicius.mbtest.features.exchanges.impl.data.local.database.ExchangeDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    single {
        Room.databaseBuilder(
            androidApplication(),
            ExchangeDatabase::class.java,
            ExchangeDatabase.DATABASE_NAME
        ).build()
    }
}