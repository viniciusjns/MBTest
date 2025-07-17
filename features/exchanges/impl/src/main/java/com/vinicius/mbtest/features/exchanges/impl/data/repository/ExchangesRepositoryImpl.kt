package com.vinicius.mbtest.features.exchanges.impl.data.repository

import com.vinicius.mbtest.features.exchanges.impl.domain.mapper.toDomain
import com.vinicius.mbtest.features.exchanges.domain.model.Exchange
import com.vinicius.mbtest.features.exchanges.domain.repository.ExchangesRepository
import com.vinicius.mbtest.features.exchanges.impl.data.local.datasource.ExchangesLocalDataSource
import com.vinicius.mbtest.features.exchanges.impl.data.remote.datasource.ExchangesRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExchangesRepositoryImpl(
    private val remoteDataSource: ExchangesRemoteDataSource,
    private val localDataSource: ExchangesLocalDataSource
) : ExchangesRepository {

    override fun getExchanges(): Flow<List<Exchange>> {
        return remoteDataSource.getExchanges().map { response ->
            localDataSource.addExchanges(response)
            response.map { it.toDomain() }
        }
    }

    override fun getExchangeById(exchangeId: String): Exchange? =
        localDataSource.getExchangeById(id = exchangeId)?.toDomain()
}