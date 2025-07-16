package com.vinicius.mbtest.presentation.viewModel

import com.vinicius.mbtest.core.BaseViewModel
import com.vinicius.mbtest.core.IViewIntent
import com.vinicius.mbtest.domain.useCase.GetExchangeByIdUseCase
import com.vinicius.mbtest.presentation.action.ExchangeDetailAction
import com.vinicius.mbtest.presentation.mapper.toDataUi
import com.vinicius.mbtest.presentation.state.ExchangeDetailViewState

sealed class ExchangeDetailViewIntent : IViewIntent {
    data class GetExchangeById(val exchangeId: String) : ExchangeDetailViewIntent()
}

class ExchangeDetailViewModel(
    private val getExchangeByIdUseCase: GetExchangeByIdUseCase
) : BaseViewModel<ExchangeDetailViewState, ExchangeDetailAction, ExchangeDetailViewIntent>(ExchangeDetailViewState()) {

    override fun dispatchViewIntent(intent: ExchangeDetailViewIntent) {
        when (intent) {
            is ExchangeDetailViewIntent.GetExchangeById -> getExchangeById(intent.exchangeId)
        }
    }

    private fun getExchangeById(exchangeId: String) {
        setState { this.copy(exchange = getExchangeByIdUseCase(exchangeId)?.toDataUi()) }
    }
}