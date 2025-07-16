package com.vinicius.mbtest.presentation.screen.exchangeDetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.vinicius.mbtest.R
import com.vinicius.mbtest.core.ErrorScreen
import com.vinicius.mbtest.presentation.action.ExchangeDetailAction
import com.vinicius.mbtest.presentation.viewModel.ExchangeDetailViewIntent
import com.vinicius.mbtest.presentation.viewModel.ExchangeDetailViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ExchangeDetailScreen(
    navController: NavHostController,
    exchangeId: String? = null
) {
    val viewModel: ExchangeDetailViewModel = koinViewModel()
    val viewState = viewModel.state.collectAsState()

    if (exchangeId == null) {
        ErrorScreen(
            errorMessage = stringResource(R.string.exchange_id_missing),
            errorButtonText = stringResource(R.string.go_back)
        ) {
            navController.popBackStack()
        }
        return
    }
    LaunchedEffect(exchangeId) {
        viewModel.dispatchViewIntent(ExchangeDetailViewIntent.GetExchangeById(exchangeId))
    }
    LaunchedEffect(Unit) {
        viewModel.action.collect {
            when (it) {
                ExchangeDetailAction.OnBackPressed -> navController.popBackStack()
            }
        }
    }

    viewState.value.exchange?.let {
        ExchangeDetailContentScreen(
            exchange = it,
            intent = viewModel::dispatchViewIntent
        )
    } ?: run {
        ErrorScreen(
            errorMessage = stringResource(R.string.exchange_not_found),
            errorButtonText = stringResource(R.string.go_back)
        ) {
            navController.popBackStack()
        }
    }
}
