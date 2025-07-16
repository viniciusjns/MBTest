package com.vinicius.mbtest.data.remote.datasource

import com.vinicius.mbtest.data.remote.model.ExchangeResponse

interface ExchangesLocalDataSource {

    fun getExchanges(): List<ExchangeResponse>
    suspend fun addExchanges(exchanges: List<ExchangeResponse>)
    fun getExchangeById(id: String): ExchangeResponse?

}