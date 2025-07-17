package com.vinicius.mbtest.features.exchanges.impl.presentation.state

import com.vinicius.mbtest.core.viewModel.IViewState
import com.vinicius.mbtest.features.exchanges.impl.presentation.model.ExchangeDataUi

data class ExchangesViewState(
    val exchanges: List<ExchangeDataUi> = emptyList(),
    val syncState: ExchangesSyncState = ExchangesSyncState.Loading
) : IViewState

sealed class ExchangesSyncState {
    object Loading : ExchangesSyncState()
    object Success : ExchangesSyncState()
    data class Error(val message: String) : ExchangesSyncState()
}