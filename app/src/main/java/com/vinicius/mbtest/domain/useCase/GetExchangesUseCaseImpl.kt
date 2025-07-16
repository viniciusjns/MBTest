package com.vinicius.mbtest.domain.useCase

import com.vinicius.mbtest.domain.repository.ExchangesRepository

class GetExchangesUseCaseImpl(
    private val repository: ExchangesRepository
) : GetExchangesUseCase {

    override operator fun invoke() = repository.getExchanges()
}