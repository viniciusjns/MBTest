package com.vinicius.mbtest.features.exchanges.impl.presentation.screen.exchanges

import ExchangesViewIntent
import ExchangesViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.vinicius.mbtest.core.extensions.observeAsActions
import com.vinicius.mbtest.core.screen.ErrorScreen
import com.vinicius.mbtest.core.screen.LoadingScreen
import com.vinicius.mbtest.features.exchanges.impl.R
import com.vinicius.mbtest.features.exchanges.impl.navigation.ScreenRoutes
import com.vinicius.mbtest.features.exchanges.impl.presentation.action.ExchangesAction
import com.vinicius.mbtest.features.exchanges.impl.presentation.state.ExchangesSyncState
import com.vinicius.mbtest.features.exchanges.impl.presentation.state.ExchangesViewState
import org.koin.androidx.compose.koinViewModel

@Composable
fun ExchangeScreen(
    viewModel: ExchangesViewModel = koinViewModel(),
    navController: NavHostController
) {
    val viewState by viewModel.state.collectAsState()
    HandleActions(viewModel = viewModel, navController = navController)
    HandleState(viewModel = viewModel, viewState = viewState)
    LaunchedEffect(Unit) {
        viewModel.dispatchViewIntent(ExchangesViewIntent.FetchExchanges)
    }

}

@Composable
private fun HandleState(
    viewModel: ExchangesViewModel,
    viewState: ExchangesViewState
) {
    when (val state = viewState.syncState) {
        ExchangesSyncState.Loading -> LoadingScreen()
        is ExchangesSyncState.Error -> ErrorScreen(errorMessage = stringResource(R.string.generic_error, state.message)) {
            viewModel.dispatchViewIntent(ExchangesViewIntent.FetchExchanges)
        }
        ExchangesSyncState.Success -> ExchangeContentScreen(viewState, viewModel::dispatchViewIntent)
    }
}

@Composable
private fun HandleActions(
    viewModel: ExchangesViewModel,
    navController: NavHostController
) {
    viewModel.action.observeAsActions {
        when (it) {
            is ExchangesAction.NavigateToDetails -> {
                navController.navigate(route = ScreenRoutes.ExchangeDetailScreenRoute.passExchangeId(exchangeId = it.exchangeId))
            }
        }
    }
}
