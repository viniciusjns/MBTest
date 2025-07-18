package com.vinicius.mbtest.features.exchanges.impl.navigation

sealed class ScreenRoutes(val route: String) {
    data object ExchangeScreenRoute : ScreenRoutes(route = "exchange_screen")
    data object ExchangeDetailScreenRoute : ScreenRoutes(route = "exchange_detail/{exchangeId}") {
        fun passExchangeId(exchangeId: String): String {
            return "exchange_detail/$exchangeId"
        }
    }
}