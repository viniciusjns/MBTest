package com.vinicius.mbtest.features.exchanges.impl.data.remote.datasource

import com.vinicius.mbtest.features.exchanges.data.remote.model.ExchangeResponse
import kotlinx.coroutines.flow.Flow

interface ExchangesRemoteDataSource {

    fun getExchanges(): Flow<List<ExchangeResponse>>
}