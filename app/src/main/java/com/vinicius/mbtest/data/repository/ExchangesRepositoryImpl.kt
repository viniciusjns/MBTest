package com.vinicius.mbtest.data.repository

import com.vinicius.mbtest.data.remote.datasource.ExchangesLocalDataSource
import com.vinicius.mbtest.data.remote.datasource.ExchangesRemoteDataSource
import com.vinicius.mbtest.domain.repository.ExchangesRepository
import com.vinicius.mbtest.domain.mapper.toDomain
import com.vinicius.mbtest.domain.model.Exchange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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