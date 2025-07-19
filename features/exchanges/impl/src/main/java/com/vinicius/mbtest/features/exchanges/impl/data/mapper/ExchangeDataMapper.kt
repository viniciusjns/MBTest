package com.vinicius.mbtest.features.exchanges.impl.data.mapper

import com.vinicius.mbtest.features.exchanges.data.local.model.ExchangeEntity
import com.vinicius.mbtest.features.exchanges.data.remote.model.ExchangeResponse
import com.vinicius.mbtest.features.exchanges.domain.model.Exchange

fun ExchangeResponse.toEntity(): ExchangeEntity = ExchangeEntity(
    exchangeId = requireNotNull(this.exchangeId) { "ExchangeId cannot be null" },
    website = this.website,
    name = this.name.orEmpty(),
    dataQuoteStart = this.dataQuoteStart,
    dataQuoteEnd = this.dataQuoteEnd,
    dataOrderBookStart = this.dataOrderBookStart,
    dataOrderBookEnd = this.dataOrderBookEnd,
    dataTradeStart = this.dataTradeStart,
    dataTradeEnd = this.dataTradeEnd,
    dataSymbolsCount = this.dataSymbolsCount,
    volume1hrsUsd = this.volume1hrsUsd ?: 0.0,
    volume1dayUsd = this.volume1dayUsd,
    volume1mthUsd = this.volume1mthUsd
)

fun ExchangeEntity.toDomain(): Exchange = Exchange(
    exchangeId = this.exchangeId,
    website = this.website,
    name = this.name,
    dataQuoteStart = this.dataQuoteStart,
    dataQuoteEnd = this.dataQuoteEnd,
    dataOrderBookStart = this.dataOrderBookStart,
    dataOrderBookEnd = this.dataOrderBookEnd,
    dataTradeStart = this.dataTradeStart,
    dataTradeEnd = this.dataTradeEnd,
    dataSymbolsCount = this.dataSymbolsCount,
    volume1hrsUsd = this.volume1hrsUsd,
    volume1dayUsd = this.volume1dayUsd,
    volume1mthUsd = this.volume1mthUsd
)