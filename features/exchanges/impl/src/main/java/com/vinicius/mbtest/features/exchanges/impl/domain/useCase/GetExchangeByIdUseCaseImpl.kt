package com.vinicius.mbtest.features.exchanges.impl.domain.useCase

import com.vinicius.mbtest.features.exchanges.domain.repository.ExchangesRepository
import com.vinicius.mbtest.features.exchanges.domain.useCase.GetExchangeByIdUseCase

class GetExchangeByIdUseCaseImpl(
    private val repository: ExchangesRepository,
) : GetExchangeByIdUseCase {

    override operator fun invoke(exchangeId: String) =
        repository.getExchangeById(exchangeId)
}