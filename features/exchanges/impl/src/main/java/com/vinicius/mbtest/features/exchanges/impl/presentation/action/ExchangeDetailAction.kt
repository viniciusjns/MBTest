package com.vinicius.mbtest.features.exchanges.impl.presentation.action

import com.vinicius.mbtest.core.viewModel.IAction

sealed class ExchangeDetailAction : IAction {
    object OnBackPressed : ExchangeDetailAction()
}