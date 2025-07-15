package com.vinicius.mbtest.domain.useCase

import com.vinicius.mbtest.domain.GetExchangesRepository

class GetExchangesUseCaseImpl(
    private val repository: GetExchangesRepository
) : GetExchangesUseCase {

    override operator fun invoke() = repository.getExchanges()
}