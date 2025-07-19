package com.vinicius.mbtest.features.exchanges.impl.data.repository

import com.vinicius.mbtest.features.exchanges.impl.domain.mapper.toDomain
import com.vinicius.mbtest.features.exchanges.domain.model.Exchange
import com.vinicius.mbtest.features.exchanges.domain.repository.ExchangesRepository
import com.vinicius.mbtest.features.exchanges.impl.data.local.datasource.ExchangesLocalDataSource
import com.vinicius.mbtest.features.exchanges.impl.data.mapper.toDomain
import com.vinicius.mbtest.features.exchanges.impl.data.mapper.toEntity
import com.vinicius.mbtest.features.exchanges.impl.data.remote.datasource.ExchangesRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class ExchangesRepositoryImpl(
    private val remoteDataSource: ExchangesRemoteDataSource,
    private val localDataSource: ExchangesLocalDataSource
) : ExchangesRepository {

    override fun getExchanges(): Flow<List<Exchange>> {
        return remoteDataSource.getExchanges()
            .map { response ->
                val entities = response.map { it.toEntity() }
                localDataSource.addExchanges(entities)
                response.map { it.toDomain() }
            }
            .catch {
                val localExchanges = localDataSource.getExchanges()
                if (localExchanges.isEmpty()) {
                    throw it
                }
                emit(localExchanges.map { exchanges -> exchanges.toDomain() })
            }
    }

    override suspend fun getExchangeById(exchangeId: String): Exchange? =
        localDataSource.getExchangeById(id = exchangeId)?.toDomain()
}