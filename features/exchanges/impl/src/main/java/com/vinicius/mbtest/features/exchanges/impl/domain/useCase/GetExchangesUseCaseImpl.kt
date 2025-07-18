package com.vinicius.mbtest.features.exchanges.impl.domain.useCase

import com.vinicius.mbtest.features.exchanges.domain.model.Exchange
import com.vinicius.mbtest.features.exchanges.domain.repository.ExchangesRepository
import com.vinicius.mbtest.features.exchanges.domain.useCase.GetExchangesUseCase
import kotlinx.coroutines.flow.Flow

class GetExchangesUseCaseImpl(
    private val repository: ExchangesRepository
) : GetExchangesUseCase {

    override operator fun invoke(): Flow<List<Exchange>> = repository.getExchanges()
}
