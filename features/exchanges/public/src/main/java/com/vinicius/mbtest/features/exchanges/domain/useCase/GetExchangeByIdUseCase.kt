package com.vinicius.mbtest.features.exchanges.domain.useCase

import com.vinicius.mbtest.features.exchanges.domain.model.Exchange

interface GetExchangeByIdUseCase {
    operator fun invoke(exchangeId: String): Exchange?
}