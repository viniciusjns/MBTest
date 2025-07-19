package com.vinicius.mbtest.features.exchanges.impl.presentation.viewModel

import androidx.lifecycle.viewModelScope
import com.vinicius.mbtest.core.viewModel.BaseViewModel
import com.vinicius.mbtest.core.viewModel.IViewIntent
import com.vinicius.mbtest.features.exchanges.domain.useCase.GetExchangeByIdUseCase
import com.vinicius.mbtest.features.exchanges.impl.presentation.action.ExchangeDetailAction
import com.vinicius.mbtest.features.exchanges.impl.presentation.mapper.toDataUi
import com.vinicius.mbtest.features.exchanges.impl.presentation.model.ExchangeDataUi
import com.vinicius.mbtest.features.exchanges.impl.presentation.state.ExchangeDetailSyncState
import com.vinicius.mbtest.features.exchanges.impl.presentation.state.ExchangeDetailViewState
import kotlinx.coroutines.launch

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
        viewModelScope.launch {
            runCatching { getExchangeByIdUseCase(exchangeId)?.toDataUi() }
                .onSuccess { handleSuccess(it) }
                .onFailure { handleError() }
        }
    }

    private fun handleSuccess(exchangeDataUi: ExchangeDataUi?) {
        setState { this.copy(exchange = exchangeDataUi, syncState = ExchangeDetailSyncState.Success) }
    }

    private fun handleError() {
        setState { this.copy(syncState = ExchangeDetailSyncState.Error) }
    }

    private fun onBackPressed() {
        sendAction { ExchangeDetailAction.OnBackPressed }
    }
}