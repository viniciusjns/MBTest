package com.vinicius.mbtest.presentation.screen.exchangeDetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.vinicius.mbtest.R
import com.vinicius.mbtest.core.ErrorScreen
import com.vinicius.mbtest.core.observeAsActions
import com.vinicius.mbtest.presentation.action.ExchangeDetailAction
import com.vinicius.mbtest.presentation.state.ExchangeDetailSyncState
import com.vinicius.mbtest.presentation.state.ExchangeDetailViewState
import com.vinicius.mbtest.presentation.viewModel.ExchangeDetailViewIntent
import com.vinicius.mbtest.presentation.viewModel.ExchangeDetailViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ExchangeDetailScreen(
    navController: NavHostController,
    exchangeId: String
) {
    val viewModel: ExchangeDetailViewModel = koinViewModel()
    val viewState by viewModel.state.collectAsState()
    HandleActions(viewModel = viewModel, navController = navController)
    HandleState(viewModel = viewModel, viewState = viewState)
    LaunchedEffect(exchangeId) {
        viewModel.dispatchViewIntent(ExchangeDetailViewIntent.GetExchangeById(exchangeId))
    }
}

@Composable
private fun HandleState(
    viewModel: ExchangeDetailViewModel,
    viewState: ExchangeDetailViewState
) {
    viewState.exchange?.let { exchangeDataUi ->
        when (viewState.syncState) {
            ExchangeDetailSyncState.Success -> {
                ExchangeDetailContentScreen(
                    exchange = exchangeDataUi,
                    intent = viewModel::dispatchViewIntent
                )
            }
            else -> {
                ShowErrorScreen(
                    viewModel = viewModel,
                    errorMessage = stringResource(R.string.exchange_not_found),
                    errorButtonText = stringResource(R.string.go_back)
                )
            }
        }
    }
}

@Composable
private fun HandleActions(
    viewModel: ExchangeDetailViewModel,
    navController: NavHostController
) {
    viewModel.action.observeAsActions {
        when (it) {
            is ExchangeDetailAction.OnBackPressed -> navController.popBackStack()
        }
    }
}

@Composable
private fun ShowErrorScreen(
    viewModel: ExchangeDetailViewModel,
    errorMessage: String,
    errorButtonText: String
) {
    ErrorScreen(
        errorMessage = errorMessage,
        errorButtonText = errorButtonText
    ) {
        viewModel.dispatchViewIntent(ExchangeDetailViewIntent.OnBackPressed)
    }
}
