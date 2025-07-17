package com.vinicius.mbtest.features.exchanges.impl.navigation

sealed class Screen(val route: String) {
    data object ExchangeScreen : Screen(route = "exchange_screen")
    data object ExchangeDetailScreen : Screen(route = "exchange_detail/{exchangeId}") {
        fun passExchangeId(exchangeId: String): String {
            return "exchange_detail/$exchangeId"
        }
    }
}