package com.vinicius.mbtest.presentation.screen.exchangeDetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import com.vinicius.mbtest.core.ErrorScreen
import com.vinicius.mbtest.presentation.viewModel.ExchangeDetailViewIntent
import com.vinicius.mbtest.presentation.viewModel.ExchangeDetailViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ExchangeDetailScreen(
    navController: NavHostController
) {
    val args = navController.currentBackStackEntry?.arguments
    val exchangeId = args?.getString("exchangeId")
    val viewModel: ExchangeDetailViewModel = koinViewModel()
    val viewState = viewModel.state.collectAsState()

    if (exchangeId == null) {
        ErrorScreen(
            errorMessage = "Exchange ID is missing",
            errorButtonText = "Go back"
        ) {
            navController.popBackStack()
        }
        return
    }
    LaunchedEffect(exchangeId) {
        viewModel.dispatchViewIntent(ExchangeDetailViewIntent.GetExchangeById(exchangeId))
    }

    viewState.value.exchange?.let {
        ExchangeDetailContentScreen(
            exchange = it
        )
    } ?: run {
        ErrorScreen(
            errorMessage = "Exchange not found",
            errorButtonText = "Go back"
        ) {
            navController.popBackStack()
        }
    }
}
