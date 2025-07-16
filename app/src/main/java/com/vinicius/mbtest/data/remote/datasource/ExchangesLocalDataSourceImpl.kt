package com.vinicius.mbtest.data.remote.datasource

import com.vinicius.mbtest.data.remote.model.ExchangeResponse

class ExchangesLocalDataSourceImpl : ExchangesLocalDataSource {

    private val exchanges = mutableListOf<ExchangeResponse>()

    override fun getExchanges(): List<ExchangeResponse> {
        return exchanges
    }

    override suspend fun addExchanges(exchanges: List<ExchangeResponse>) {
        this.exchanges.addAll(exchanges)
    }

    override fun getExchangeById(id: String): ExchangeResponse? {
        return exchanges.find { it.exchangeId == id }
    }
}