package com.vinicius.mbtest.data.repository

import com.vinicius.mbtest.data.remote.datasource.GetExchangesRemoteDataSource
import com.vinicius.mbtest.domain.repository.GetExchangesRepository
import com.vinicius.mbtest.domain.mapper.toDomain
import com.vinicius.mbtest.domain.model.Exchange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetExchangesRepositoryImpl(
    private val remoteDataSource: GetExchangesRemoteDataSource
) : GetExchangesRepository {

    override fun getExchanges(): Flow<List<Exchange>> =
        remoteDataSource.getExchanges().map { response ->
            response.map { it.toDomain() }
        }
}