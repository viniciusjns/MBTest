package com.vinicius.mbtest.features.exchanges.impl.data.remote.datasource

import com.vinicius.mbtest.core.extensions.parseHttpError
import com.vinicius.mbtest.features.exchanges.data.remote.model.ExchangeResponse
import com.vinicius.mbtest.features.exchanges.data.remote.model.IconResponse
import com.vinicius.mbtest.features.exchanges.impl.data.remote.api.CoinService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ExchangesRemoteDataSourceImpl(
    private val service: CoinService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ExchangesRemoteDataSource {

    override fun getExchanges(): Flow<List<ExchangeResponse>> = flow {
        emit(service.getExchanges().getValidExchanges())
    }.parseHttpError().flowOn(dispatcher)

    override fun getExchangesIcons(): Flow<List<IconResponse>> = flow {
        emit(service.getExchangesIcons())
    }.parseHttpError().flowOn(dispatcher)

    private fun List<ExchangeResponse>.getValidExchanges() = this.filter {
        it.exchangeId != null && it.name != null && it.volume1dayUsd != null
    }
}