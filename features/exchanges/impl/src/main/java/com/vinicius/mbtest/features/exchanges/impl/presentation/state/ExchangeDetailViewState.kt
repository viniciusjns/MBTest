package com.vinicius.mbtest.features.exchanges.impl.presentation.state

import com.vinicius.mbtest.core.viewModel.IViewState
import com.vinicius.mbtest.features.exchanges.impl.presentation.model.ExchangeDataUi

data class ExchangeDetailViewState(
    val exchange: ExchangeDataUi? = null,
    val syncState: ExchangeDetailSyncState? = null
) : IViewState

sealed class ExchangeDetailSyncState {
    object Success : ExchangeDetailSyncState()
    object Error : ExchangeDetailSyncState()
}
