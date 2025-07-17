package com.vinicius.mbtest.features.exchanges.impl.data.remote.api

import com.vinicius.mbtest.features.exchanges.data.remote.model.ExchangeResponse
import retrofit2.http.GET

interface CoinService {

    @GET("/v1/exchanges")
    suspend fun getExchanges(): List<ExchangeResponse>
}