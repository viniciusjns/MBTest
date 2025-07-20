package com.vinicius.mbtest.features.exchanges.impl.data.remote.api

import com.vinicius.mbtest.features.exchanges.data.remote.model.ExchangeResponse
import com.vinicius.mbtest.features.exchanges.data.remote.model.IconResponse
import retrofit2.http.GET

interface CoinService {

    @GET("/v1/exchanges")
    suspend fun getExchanges(): List<ExchangeResponse>

    @GET("/v1/exchanges/icons/64")
    suspend fun getExchangesIcons(): List<IconResponse>
}