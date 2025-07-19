package com.vinicius.mbtest.features.exchanges.domain.model

data class Exchange(
    val exchangeId: String,
    val website: String?,
    val name: String,
    val dataQuoteStart: String?,
    val dataQuoteEnd: String?,
    val dataOrderBookStart: String?,
    val dataOrderBookEnd: String?,
    val dataTradeStart: String?,
    val dataTradeEnd: String?,
    val dataSymbolsCount: Int?,
    val volume1hrsUsd: Double,
    val volume1dayUsd: Double?,
    val volume1mthUsd: Double?
)