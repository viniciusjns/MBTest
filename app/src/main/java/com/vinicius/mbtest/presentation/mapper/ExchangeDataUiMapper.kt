package com.vinicius.mbtest.presentation.mapper

import com.vinicius.mbtest.domain.model.Exchange
import com.vinicius.mbtest.presentation.model.ExchangeDataUi

fun Exchange.toDataUi(): ExchangeDataUi = ExchangeDataUi(
    exchangeId = this.exchangeId,
    website = this.website,
    name = this.name,
    dataQuoteStart = this.dataQuoteStart,
    dataQuoteEnd = this.dataQuoteEnd,
    dataOrderBookStart = this.dataOrderBookStart,
    dataOrderBookEnd = this.dataOrderBookEnd,
    dataTradeStart = this.dataTradeStart,
    dataTradeEnd = this.dataTradeEnd,
    dataSymbolsCount = this.dataSymbolsCount.toString(),
    volume1hrsUsd = this.volume1hrsUsd.toString(),
    volume1dayUsd = this.volume1dayUsd.toString(),
    volume1mthUsd = this.volume1mthUsd.toString()
)