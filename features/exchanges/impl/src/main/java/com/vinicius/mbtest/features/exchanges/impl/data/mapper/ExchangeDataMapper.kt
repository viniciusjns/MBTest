package com.vinicius.mbtest.features.exchanges.impl.data.mapper

import com.vinicius.mbtest.features.exchanges.data.local.model.ExchangeEntity
import com.vinicius.mbtest.features.exchanges.data.remote.model.ExchangeResponse
import com.vinicius.mbtest.features.exchanges.data.remote.model.IconResponse
import com.vinicius.mbtest.features.exchanges.domain.model.Exchange
import com.vinicius.mbtest.features.exchanges.impl.domain.mapper.toDomain

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
    volume1mthUsd = this.volume1mthUsd,
    iconUrl = this.iconUrl
)

fun Exchange.toEntity(): ExchangeEntity = ExchangeEntity(
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
    volume1mthUsd = this.volume1mthUsd,
    iconUrl = this.iconUrl
)

fun List<ExchangeResponse>.mapWithIcons(icons: List<IconResponse>): List<Exchange> {
    return map { exchangeResponse ->
        val iconUrl = icons.find { it.exchangeId == exchangeResponse.exchangeId }?.url
        iconUrl?.let {
            exchangeResponse.toDomain().copy(iconUrl = iconUrl)
        } ?: exchangeResponse.toDomain()
    }
}