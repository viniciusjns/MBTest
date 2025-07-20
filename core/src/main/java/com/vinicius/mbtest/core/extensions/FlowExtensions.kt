package com.vinicius.mbtest.core.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@Composable
inline fun <reified T> Flow<T>.observeAsActions(
    crossinline block: suspend (T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(key1 = this, lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            this@observeAsActions.flowWithLifecycle(
                lifecycleOwner.lifecycle,
                Lifecycle.State.STARTED
            ).collect { block(it) }
        }
    }
}

fun <T> CoroutineScope.launchFlow(
    data: () -> Flow<T>,
    onStart: (() -> Unit)? = null,
    onSuccess: (T) -> Unit,
    onError: (Throwable) -> Unit
) {
    this.launch {
        data()
            .onStart { onStart?.invoke() }
            .catch { exception -> onError(exception) }
            .collect { result -> onSuccess(result) }
    }
}