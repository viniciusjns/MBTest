package com.vinicius.mbtest.data.remote.api

import com.vinicius.mbtest.data.remote.model.ExchangeResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface CoinService {

    @GET("/v1/exchanges")
    suspend fun getExchanges(): List<ExchangeResponse>
}