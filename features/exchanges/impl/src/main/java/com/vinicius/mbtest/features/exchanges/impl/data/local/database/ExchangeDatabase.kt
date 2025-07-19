package com.vinicius.mbtest.features.exchanges.impl.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vinicius.mbtest.features.exchanges.data.local.model.ExchangeEntity
import com.vinicius.mbtest.features.exchanges.impl.data.local.dao.ExchangeDAO

@Database(entities = [ExchangeEntity::class], version = 1)
abstract class ExchangeDatabase : RoomDatabase() {

    abstract fun exchangeDao(): ExchangeDAO

    companion object {
        const val DATABASE_NAME = "exchange_database"
    }
}