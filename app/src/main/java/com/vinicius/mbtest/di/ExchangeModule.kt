package com.vinicius.mbtest.di

import ExchangesViewModel
import com.vinicius.mbtest.data.remote.api.CoinService
import com.vinicius.mbtest.data.remote.datasource.GetExchangesRemoteDataSourceImpl
import com.vinicius.mbtest.data.repository.GetExchangesRepositoryImpl
import com.vinicius.mbtest.domain.useCase.GetExchangesUseCaseImpl
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val exchangeModule = module {
    viewModel {
        ExchangesViewModel(
            getExchangesUseCase = GetExchangesUseCaseImpl(
                repository = GetExchangesRepositoryImpl(
                    remoteDataSource = GetExchangesRemoteDataSourceImpl(
                        service = getExchangeService(retrofit = get())
                    )
                )
            )
        )
    }
}

private fun getExchangeService(retrofit: Retrofit) = retrofit.create(CoinService::class.java)