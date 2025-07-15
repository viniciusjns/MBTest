package com.vinicius.mbtest.data.remote.datasource

import com.vinicius.mbtest.data.remote.model.ExchangeResponse
import kotlinx.coroutines.flow.Flow

interface GetExchangesRemoteDataSource {

    fun getExchanges(): Flow<List<ExchangeResponse>>
}