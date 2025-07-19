package com.vinicius.mbtest.features.exchanges.impl.stub

import com.vinicius.mbtest.features.exchanges.data.local.model.ExchangeEntity
import com.vinicius.mbtest.features.exchanges.domain.model.Exchange

fun exchangesStub() = (1..20).map { i ->
    Exchange(
        exchangeId = "exch_$i",
        website = "https://exchange$i.com",
        name = "Exchange $i",
        dataQuoteStart = "2020-01-01",
        dataQuoteEnd = "2025-01-01",
        dataOrderBookStart = "2020-02-01",
        dataOrderBookEnd = "2025-02-01",
        dataTradeStart = "2020-03-01",
        dataTradeEnd = "2025-03-01",
        dataSymbolsCount = i * 100,
        volume1hrsUsd = i * 1000.0,
        volume1dayUsd = i * 5000.0,
        volume1mthUsd = i * 20000.0
    )
}

fun exchangesEntityStub() = (1..20).map { i ->
    ExchangeEntity(
        exchangeId = "exch_$i",
        website = "https://exchange$i.com",
        name = "Exchange $i",
        dataQuoteStart = "2020-01-01",
        dataQuoteEnd = "2025-01-01",
        dataOrderBookStart = "2020-02-01",
        dataOrderBookEnd = "2025-02-01",
        dataTradeStart = "2020-03-01",
        dataTradeEnd = "2025-03-01",
        dataSymbolsCount = i * 100,
        volume1hrsUsd = i * 1000.0,
        volume1dayUsd = i * 5000.0,
        volume1mthUsd = i * 20000.0
    )
}

fun exchangeStub() = exchangesStub().first()