package com.vinicius.mbtest.features.exchanges.domain.useCase

import com.vinicius.mbtest.features.exchanges.domain.model.Exchange
import kotlinx.coroutines.flow.Flow

interface GetExchangesUseCase {
    operator fun invoke(): Flow<List<Exchange>>
}