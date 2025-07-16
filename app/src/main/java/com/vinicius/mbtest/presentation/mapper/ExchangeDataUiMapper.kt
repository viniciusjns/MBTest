package com.vinicius.mbtest.presentation.mapper

import com.vinicius.mbtest.core.formatToBrazilianDate
import com.vinicius.mbtest.core.toMoney
import com.vinicius.mbtest.domain.model.Exchange
import com.vinicius.mbtest.presentation.model.ExchangeDataUi

fun Exchange.toDataUi(): ExchangeDataUi = ExchangeDataUi(
    exchangeId = exchangeId,
    website = website,
    name = name,
    dataQuoteStart = dataQuoteStart?.formatToBrazilianDate(),
    dataQuoteEnd = dataQuoteEnd?.formatToBrazilianDate(),
    dataOrderBookStart = dataOrderBookStart?.formatToBrazilianDate(),
    dataOrderBookEnd = dataOrderBookEnd?.formatToBrazilianDate(),
    dataTradeStart = dataTradeStart?.formatToBrazilianDate(),
    dataTradeEnd = dataTradeEnd?.formatToBrazilianDate(),
    dataSymbolsCount = dataSymbolsCount.toString(),
    volume1hrsUsd = volume1hrsUsd.toString().toMoney(),
    volume1dayUsd = volume1dayUsd.toString().toMoney(),
    volume1mthUsd = volume1mthUsd.toString().toMoney(),
)