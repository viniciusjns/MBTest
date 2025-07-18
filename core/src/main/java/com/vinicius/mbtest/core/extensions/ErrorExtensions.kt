package com.vinicius.mbtest.core.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

fun <T> Flow<T>.parseHttpError(): Flow<T> {
    return catch { throwable ->
        throw throwable
    }
}