package com.vinicius.mbtest.features.exchanges.impl.data.remote.datasource

import com.vinicius.mbtest.features.exchanges.data.remote.model.ExchangeResponse
import com.vinicius.mbtest.features.exchanges.impl.data.remote.api.CoinService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ExchangesRemoteDataSourceImpl(
    private val service: CoinService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ExchangesRemoteDataSource {

    override fun getExchanges(): Flow<List<ExchangeResponse>> = flow {
        emit(service.getExchanges().getValidExchanges())
    }.catch {
        throw it
    }.flowOn(dispatcher)

    private fun List<ExchangeResponse>.getValidExchanges() = this.filter {
        it.exchangeId != null && it.name != null && it.volume1dayUsd != null
    }

    private fun mockExchanges(): List<ExchangeResponse> = (1..20).map { i ->
        ExchangeResponse(
            exchangeId = "exch_$i",
            website = "https://exchange$i.com",
            name = "Exchange $i",
            dataQuoteStart = "2020-01-01",
            dataQuoteEnd = "2025-01-01",
            dataOrderBookStart = "2020-02-01",
            dataOrderBookEnd = "2025-02-01",
            dataTradeStart = "2020-03-01",
            dataTradeEnd = "2025-03-01",
            dataSymbolsCount = i * 100,
            volume1hrsUsd = i * 1000.0,
            volume1dayUsd = i * 5000.0,
            volume1mthUsd = i * 20000.0
        )
    }
}