package com.vinicius.mbtest.features.exchanges.domain.repository

import com.vinicius.mbtest.features.exchanges.domain.model.Exchange
import kotlinx.coroutines.flow.Flow

interface ExchangesRepository {
    fun getExchanges(): Flow<List<Exchange>>
    suspend fun getExchangeById(exchangeId: String): Exchange?
}