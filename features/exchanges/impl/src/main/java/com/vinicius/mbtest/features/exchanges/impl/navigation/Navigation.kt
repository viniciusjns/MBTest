package com.vinicius.mbtest.features.exchanges.impl.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.vinicius.mbtest.features.exchanges.impl.presentation.screen.exchangeDetail.ExchangeDetailScreen
import com.vinicius.mbtest.features.exchanges.impl.presentation.screen.exchanges.ExchangeScreen

@Composable
fun SetupNavigation(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = ScreenRoutes.ExchangeScreenRoute.route) {
            ExchangeScreen(navController = navController)
        }
        composable(
            route = ScreenRoutes.ExchangeDetailScreenRoute.route,
            arguments = listOf(navArgument("exchangeId") { type = NavType.StringType })
        ) { backStackEntry ->
            val exchangeId = backStackEntry.arguments?.getString("exchangeId").orEmpty()
            ExchangeDetailScreen(navController = navController, exchangeId)
        }
    }
}