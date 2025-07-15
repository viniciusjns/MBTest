package com.vinicius.mbtest.domain

import com.vinicius.mbtest.domain.model.Exchange
import kotlinx.coroutines.flow.Flow

interface GetExchangesRepository {
    fun getExchanges(): Flow<List<Exchange>>
}