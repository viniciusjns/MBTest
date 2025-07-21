package com.vinicius.mbtest.core.remote.model

class HttpThrowable(
    val httpError: HttpError,
    cause: Throwable? = null
) : Throwable(httpError.message, cause)