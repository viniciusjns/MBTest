package com.vinicius.mbtest.domain.repository

import com.vinicius.mbtest.domain.model.Exchange
import kotlinx.coroutines.flow.Flow

interface ExchangesRepository {
    fun getExchanges(): Flow<List<Exchange>>
    fun getExchangeById(exchangeId: String): Exchange?
}