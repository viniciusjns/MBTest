package com.vinicius.mbtest.features.exchanges.impl.presentation.action

import com.vinicius.mbtest.core.viewModel.IAction

sealed class ExchangesAction : IAction {
    data class NavigateToDetails(val exchangeId: String) : ExchangesAction()
}