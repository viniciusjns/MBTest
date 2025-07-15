package com.vinicius.mbtest.data.remote.datasource

import com.vinicius.mbtest.data.remote.api.CoinService
import com.vinicius.mbtest.data.remote.model.ExchangeResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetExchangesRemoteDataSourceImpl(
    private val service: CoinService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : GetExchangesRemoteDataSource {

    override fun getExchanges(): Flow<List<ExchangeResponse>> = flow {
        emit(service.getExchanges())
    }.flowOn(dispatcher)
}