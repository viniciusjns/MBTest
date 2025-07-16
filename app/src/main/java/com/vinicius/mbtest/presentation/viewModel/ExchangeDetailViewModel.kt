package com.vinicius.mbtest.presentation.viewModel

import com.vinicius.mbtest.core.BaseViewModel
import com.vinicius.mbtest.core.IViewIntent
import com.vinicius.mbtest.domain.useCase.GetExchangeByIdUseCase
import com.vinicius.mbtest.presentation.action.ExchangeDetailAction
import com.vinicius.mbtest.presentation.mapper.toDataUi
import com.vinicius.mbtest.presentation.state.ExchangeDetailSyncState
import com.vinicius.mbtest.presentation.state.ExchangeDetailViewState

sealed class ExchangeDetailViewIntent : IViewIntent {
    data class GetExchangeById(val exchangeId: String) : ExchangeDetailViewIntent()
    object OnBackPressed : ExchangeDetailViewIntent()
}

class ExchangeDetailViewModel(
    private val getExchangeByIdUseCase: GetExchangeByIdUseCase
) : BaseViewModel<ExchangeDetailViewState, ExchangeDetailAction, ExchangeDetailViewIntent>(ExchangeDetailViewState()) {

    override fun dispatchViewIntent(intent: ExchangeDetailViewIntent) {
        when (intent) {
            is ExchangeDetailViewIntent.GetExchangeById -> getExchangeById(intent.exchangeId)
            ExchangeDetailViewIntent.OnBackPressed -> onBackPressed()
        }
    }

    private fun getExchangeById(exchangeId: String) {
        val exchangeDataUi = getExchangeByIdUseCase(exchangeId)?.toDataUi()
        val syncState = if (exchangeDataUi != null) {
            ExchangeDetailSyncState.Success
        } else {
            ExchangeDetailSyncState.Error
        }
        setState { this.copy(exchange = exchangeDataUi, syncState = syncState) }
    }

    private fun onBackPressed() {
        sendAction { ExchangeDetailAction.OnBackPressed }
    }
}