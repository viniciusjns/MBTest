package com.vinicius.mbtest.presentation.screen.exchanges

import ExchangesViewIntent
import ExchangesViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.vinicius.mbtest.core.ErrorScreen
import com.vinicius.mbtest.core.LoadingScreen
import com.vinicius.mbtest.presentation.state.ExchangesSyncState
import org.koin.androidx.compose.koinViewModel

@Composable
fun ExchangeScreen(
    viewModel: ExchangesViewModel = koinViewModel()
) {
    val viewState by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.dispatchViewIntent(ExchangesViewIntent.FetchExchanges)
    }

    when (val state = viewState.syncState) {
        ExchangesSyncState.Loading -> LoadingScreen()
        is ExchangesSyncState.Error -> ErrorScreen(errorMessage = "Erro: ${state.message}") {
            viewModel.dispatchViewIntent(ExchangesViewIntent.FetchExchanges)
        }
        ExchangesSyncState.Success -> ExchangeContentScreen(viewState)
    }
}
