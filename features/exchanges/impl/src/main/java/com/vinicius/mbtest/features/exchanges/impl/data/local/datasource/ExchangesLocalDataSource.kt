package com.vinicius.mbtest.features.exchanges.impl.data.local.datasource

import com.vinicius.mbtest.features.exchanges.data.local.model.ExchangeEntity

interface ExchangesLocalDataSource {

    suspend fun getExchanges(): List<ExchangeEntity>
    suspend fun addExchanges(exchanges: List<ExchangeEntity>)
    suspend fun getExchangeById(id: String): ExchangeEntity?

}