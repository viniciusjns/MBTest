package com.vinicius.mbtest.features.exchanges.impl.domain.useCase

import com.vinicius.mbtest.features.exchanges.domain.repository.ExchangesRepository

class GetExchangesUseCaseImpl(
    private val repository: ExchangesRepository
) : com.vinicius.mbtest.features.exchanges.domain.useCase.GetExchangesUseCase {

    override operator fun invoke() = repository.getExchanges()
}