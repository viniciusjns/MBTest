package com.vinicius.mbtest.features.exchanges.impl.presentation.screen.exchanges

import ExchangesViewIntent
import ExchangesViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.vinicius.mbtest.core.screen.ErrorScreen
import com.vinicius.mbtest.core.screen.LoadingScreen
import com.vinicius.mbtest.features.exchanges.impl.R
import com.vinicius.mbtest.features.exchanges.impl.presentation.state.ExchangesSyncState
import com.vinicius.mbtest.features.exchanges.impl.presentation.state.ExchangesViewState
import org.koin.androidx.compose.koinViewModel

@Composable
fun ExchangeScreen(
    viewModel: ExchangesViewModel = koinViewModel(),
    navController: NavHostController
) {
    val viewState by viewModel.state.collectAsState()
    HandleState(viewModel = viewModel, viewState = viewState, navController = navController)
    LaunchedEffect(Unit) {
        viewModel.dispatchViewIntent(ExchangesViewIntent.FetchExchanges)
    }

}

@Composable
private fun HandleState(
    viewModel: ExchangesViewModel,
    viewState: ExchangesViewState,
    navController: NavHostController
) {
    when (val state = viewState.syncState) {
        ExchangesSyncState.Loading -> LoadingScreen()
        is ExchangesSyncState.Error -> ErrorScreen(errorMessage = stringResource(R.string.generic_error, state.message)) {
            viewModel.dispatchViewIntent(ExchangesViewIntent.FetchExchanges)
        }
        ExchangesSyncState.Success -> ExchangeContentScreen(viewState, navController)
    }
}
