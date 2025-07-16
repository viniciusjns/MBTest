package com.vinicius.mbtest.data.remote.datasource

import android.util.Log
import com.vinicius.mbtest.data.remote.api.CoinService
import com.vinicius.mbtest.data.remote.model.ExchangeResponse
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
        Log.i("GetExchangesRemoteDataSourceImpl", "Fetching exchanges...")
//        emit(service.getExchanges())

//        kotlinx.coroutines.delay(1000L)
        emit(mockExchanges())

//        kotlinx.coroutines.delay(1000L)
//        emit(throw Exception())
    }.flowOn(dispatcher)

    private fun mockExchanges(): List<ExchangeResponse> {
        val exchanges = (1..20).map { i ->
            ExchangeResponse(
                exchangeId = i.toString(),
                website = "https://exchange${i % 3 + 1}.com",
                name = "Exchange $i",
                dataQuoteStart = if (i % 3 == 1) "2020-01-01" else if (i % 3 == 2) "2019-06-01" else null,
                dataQuoteEnd = if (i % 3 == 1) "2024-01-01" else if (i % 3 == 2) "2024-06-01" else null,
                dataOrderBookStart = if (i % 3 == 1) "2020-01-01" else if (i % 3 == 2) "2019-06-01" else null,
                dataOrderBookEnd = if (i % 3 == 1) "2024-01-01" else if (i % 3 == 2) "2024-06-01" else null,
                dataTradeStart = if (i % 3 == 1) "2020-01-01" else if (i % 3 == 2) "2019-06-01" else null,
                dataTradeEnd = if (i % 3 == 1) "2024-01-01" else if (i % 3 == 2) "2024-06-01" else null,
                dataSymbolsCount = if (i % 3 == 1) 100 else if (i % 3 == 2) 50 else null,
                volume1hrsUsd = if (i % 3 == 1) 5000.0 else if (i % 3 == 2) 2500.0 else null,
                volume1dayUsd = if (i % 3 == 1) 120000.0 else if (i % 3 == 2) 60000.0 else null,
                volume1mthUsd = if (i % 3 == 1) 3000000.0 else if (i % 3 == 2) 1500000.0 else null
            )
        }
        return exchanges
    }
}