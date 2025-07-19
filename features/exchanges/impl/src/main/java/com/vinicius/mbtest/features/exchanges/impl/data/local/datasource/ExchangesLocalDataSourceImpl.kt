package com.vinicius.mbtest.features.exchanges.impl.data.local.datasource

import com.vinicius.mbtest.features.exchanges.data.local.model.ExchangeEntity
import com.vinicius.mbtest.features.exchanges.impl.data.local.dao.ExchangeDAO

class ExchangesLocalDataSourceImpl(
    private val exchangeDAO: ExchangeDAO
) : ExchangesLocalDataSource {

    override suspend fun getExchanges(): List<ExchangeEntity> =
        exchangeDAO.getAllExchanges()

    override suspend fun addExchanges(exchanges: List<ExchangeEntity>) =
        exchanges.forEach { exchangeDAO.insertExchange(it) }

    override suspend fun getExchangeById(id: String): ExchangeEntity? =
        exchangeDAO.getExchangeById(id = id)
}