package com.vinicius.mbtest.features.exchanges.impl.data.local.datasource

import com.vinicius.mbtest.features.exchanges.data.remote.model.ExchangeResponse

interface ExchangesLocalDataSource {

    fun getExchanges(): List<ExchangeResponse>
    suspend fun addExchanges(exchanges: List<ExchangeResponse>)
    fun getExchangeById(id: String): ExchangeResponse?

}