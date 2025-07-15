package com.vinicius.mbtest.core

import androidx.lifecycle.ViewModel
import com.vinicius.mbtest.presentation.action.ExchangesAction
import com.vinicius.mbtest.presentation.state.ExchangesViewState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface IViewState

interface IAction

interface IViewIntent

abstract class BaseViewModel<VS : IViewState, A : IAction, VI: IViewIntent>(initialViewState: VS) : ViewModel() {

    // StateFlow for UI state
    private val _state = MutableStateFlow(initialViewState)
    val state: StateFlow<VS> = _state.asStateFlow()

    // SharedFlow for one-time UI events (effects)
    private val _action = MutableSharedFlow<A>()
    val action: SharedFlow<A> = _action.asSharedFlow()

    protected fun setState(block: VS.() -> VS) {
        _state.update { block(it) }
    }

    protected fun sendAction(block: () -> A) {
        _action.tryEmit(block())
    }

    abstract fun dispatchViewIntent(intent: VI)
}