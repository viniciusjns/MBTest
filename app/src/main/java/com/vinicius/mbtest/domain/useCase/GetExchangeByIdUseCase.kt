package com.vinicius.mbtest.domain.useCase

import com.vinicius.mbtest.domain.model.Exchange

interface GetExchangeByIdUseCase {
    operator fun invoke(exchangeId: String): Exchange?
}