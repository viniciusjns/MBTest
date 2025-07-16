package com.vinicius.mbtest.domain.useCase

import com.vinicius.mbtest.domain.repository.ExchangesRepository

class GetExchangeByIdUseCaseImpl(
    private val repository: ExchangesRepository,
) : GetExchangeByIdUseCase {

    override operator fun invoke(exchangeId: String) =
        repository.getExchangeById(exchangeId)
}