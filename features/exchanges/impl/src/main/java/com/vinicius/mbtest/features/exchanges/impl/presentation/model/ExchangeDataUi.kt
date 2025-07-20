package com.vinicius.mbtest.features.exchanges.impl.presentation.model

data class ExchangeDataUi(
    val exchangeId: String,
    val website: String?,
    val name: String,
    val dataQuoteStart: String?,
    val dataQuoteEnd: String?,
    val dataOrderBookStart: String?,
    val dataOrderBookEnd: String?,
    val dataTradeStart: String?,
    val dataTradeEnd: String?,
    val dataSymbolsCount: String?,
    val volume1hrsUsd: String,
    val volume1dayUsd: String?,
    val volume1mthUsd: String?,
    val iconUrl: String?
)