package com.vinicius.mbtest.domain.useCase

import com.vinicius.mbtest.domain.model.Exchange
import kotlinx.coroutines.flow.Flow

interface GetExchangesUseCase {
    operator fun invoke(): Flow<List<Exchange>>
}