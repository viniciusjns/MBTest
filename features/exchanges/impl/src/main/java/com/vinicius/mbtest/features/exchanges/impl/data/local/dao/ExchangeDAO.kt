package com.vinicius.mbtest.features.exchanges.impl.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vinicius.mbtest.features.exchanges.data.local.model.ExchangeEntity

@Dao
interface ExchangeDAO {

     @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertExchange(exchange: ExchangeEntity)

     @Query("SELECT * FROM tb_exchange WHERE exchangeId = :id")
     suspend fun getExchangeById(id: String): ExchangeEntity?

     @Query("SELECT * FROM tb_exchange")
     suspend fun getAllExchanges(): List<ExchangeEntity>
}