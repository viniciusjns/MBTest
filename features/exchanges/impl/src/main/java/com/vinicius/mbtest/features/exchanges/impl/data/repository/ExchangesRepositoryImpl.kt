package com.vinicius.mbtest.features.exchanges.impl.data.repository

import com.vinicius.mbtest.features.exchanges.data.remote.model.ExchangeResponse
import com.vinicius.mbtest.features.exchanges.data.remote.model.IconResponse
import com.vinicius.mbtest.features.exchanges.impl.domain.mapper.toDomain
import com.vinicius.mbtest.features.exchanges.domain.model.Exchange
import com.vinicius.mbtest.features.exchanges.domain.repository.ExchangesRepository
import com.vinicius.mbtest.features.exchanges.impl.data.local.datasource.ExchangesLocalDataSource
import com.vinicius.mbtest.features.exchanges.impl.data.mapper.mapWithIcons
import com.vinicius.mbtest.features.exchanges.impl.data.mapper.toDomain
import com.vinicius.mbtest.features.exchanges.impl.data.mapper.toEntity
import com.vinicius.mbtest.features.exchanges.impl.data.remote.datasource.ExchangesRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class ExchangesRepositoryImpl(
    private val remoteDataSource: ExchangesRemoteDataSource,
    private val localDataSource: ExchangesLocalDataSource
) : ExchangesRepository {

    override fun getExchanges(): Flow<List<Exchange>> = flow {
        val exchangesResponse = remoteDataSource.getExchanges().first()
        val iconsResponse = remoteDataSource.getExchangesIcons().first()
        val exchanges = exchangesResponse.mapWithIcons(iconsResponse)

        localDataSource.addExchanges(exchanges.map { it.toEntity() })

        emit(exchanges)
    }.catch { exception ->
        val localExchanges = localDataSource.getExchanges()
        if (localExchanges.isEmpty()) throw exception
        emit(localExchanges.map { it.toDomain() })
    }

    override suspend fun getExchangeById(exchangeId: String): Exchange? =
        localDataSource.getExchangeById(id = exchangeId)?.toDomain()
}