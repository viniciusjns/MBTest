package com.vinicius.mbtest.features.exchanges.impl.data.remote.datasource

import com.vinicius.mbtest.features.exchanges.data.remote.model.ExchangeResponse
import com.vinicius.mbtest.features.exchanges.data.remote.model.IconResponse
import kotlinx.coroutines.flow.Flow

interface ExchangesRemoteDataSource {

    fun getExchanges(): Flow<List<ExchangeResponse>>

    fun getExchangesIcons(): Flow<List<IconResponse>>
}