package com.vinicius.mbtest.features.exchanges.impl.di

import ExchangesViewModel
import com.vinicius.mbtest.features.exchanges.impl.data.remote.api.CoinService
import com.vinicius.mbtest.features.exchanges.domain.repository.ExchangesRepository
import com.vinicius.mbtest.features.exchanges.impl.data.local.datasource.ExchangesLocalDataSource
import com.vinicius.mbtest.features.exchanges.impl.data.local.datasource.ExchangesLocalDataSourceImpl
import com.vinicius.mbtest.features.exchanges.impl.data.remote.datasource.ExchangesRemoteDataSourceImpl
import com.vinicius.mbtest.features.exchanges.impl.data.repository.ExchangesRepositoryImpl
import com.vinicius.mbtest.features.exchanges.impl.domain.useCase.GetExchangeByIdUseCaseImpl
import com.vinicius.mbtest.features.exchanges.impl.domain.useCase.GetExchangesUseCaseImpl
import com.vinicius.mbtest.features.exchanges.impl.presentation.viewModel.ExchangeDetailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val exchangeModule = module {
    viewModel {
        ExchangesViewModel(
            getExchangesUseCase = GetExchangesUseCaseImpl(
                repository = get()
            )
        )
    }

    viewModel {
        ExchangeDetailViewModel(
            getExchangeByIdUseCase = GetExchangeByIdUseCaseImpl(
                repository = get()
            )
        )
    }

    factory<ExchangesRepository> {
        ExchangesRepositoryImpl(
            remoteDataSource = ExchangesRemoteDataSourceImpl(
                service = getExchangeService(retrofit = get())
            ),
            localDataSource = get()
        )
    }

    single<ExchangesLocalDataSource> {
        ExchangesLocalDataSourceImpl()
    }
}

private fun getExchangeService(retrofit: Retrofit) = retrofit.create(CoinService::class.java)