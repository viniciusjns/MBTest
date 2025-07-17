package com.vinicius.mbtest.features.exchanges.impl.presentation.mapper

import com.vinicius.mbtest.core.extensions.formatToBrazilianDate
import com.vinicius.mbtest.core.extensions.toMoney
import com.vinicius.mbtest.features.exchanges.domain.model.Exchange
import com.vinicius.mbtest.features.exchanges.impl.presentation.model.ExchangeDataUi

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