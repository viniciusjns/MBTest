package com.vinicius.mbtest.presentation.state

import com.vinicius.mbtest.core.IViewState
import com.vinicius.mbtest.presentation.model.ExchangeDataUi

data class ExchangeDetailViewState(
    val exchange: ExchangeDataUi? = null,
    val syncState: ExchangeDetailSyncState? = null
) : IViewState

sealed class ExchangeDetailSyncState {
    object Success : ExchangeDetailSyncState()
    object Error : ExchangeDetailSyncState()
}
