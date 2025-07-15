package com.vinicius.mbtest.presentation.action

import com.vinicius.mbtest.core.IAction

sealed class ExchangesAction : IAction {
    data class ShowErrorSnackbar(val message: String) : ExchangesAction()
}