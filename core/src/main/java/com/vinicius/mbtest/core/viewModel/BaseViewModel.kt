package com.vinicius.mbtest.core.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<VS : IViewState, A : IAction, VI: IViewIntent>(initialViewState: VS) : ViewModel() {

    private val _state = MutableStateFlow(initialViewState)
    val state: StateFlow<VS> = _state.asStateFlow()

    private val _action = MutableSharedFlow<A>()
    val action: SharedFlow<A> = _action.asSharedFlow()

    protected fun setState(block: VS.() -> VS) {
        _state.update { block(it) }
    }

    protected fun sendAction(block: () -> A) {
        viewModelScope.launch { _action.emit(block()) }
    }

    abstract fun dispatchViewIntent(intent: VI)
}