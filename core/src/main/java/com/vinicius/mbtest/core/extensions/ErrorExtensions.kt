package com.vinicius.mbtest.core.extensions

import com.vinicius.mbtest.core.remote.model.HttpError
import com.vinicius.mbtest.core.remote.model.HttpThrowable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun <T> Flow<T>.parseHttpError(): Flow<T> {
    return catch { throwable ->
        when (this) {
            is HttpException -> {
                val httpError = HttpError.entries.find { it.code == this.code() }
                    ?: HttpError.GENERIC
                throw HttpThrowable(
                    httpError = httpError,
                    cause = this
                )
            }
            is UnknownHostException,
            is SocketTimeoutException,
            is IOException -> {
                throw HttpThrowable(
                    httpError = HttpError.INTERNET,
                    cause = throwable
                )
            }
            else -> { throw throwable }
        }
    }
}