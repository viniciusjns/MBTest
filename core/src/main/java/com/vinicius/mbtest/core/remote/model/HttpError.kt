package com.vinicius.mbtest.core.remote.model

enum class HttpError(val code: Int, val message: String) {
    BAD_REQUEST(400, "Invalid request"),
    UNAUTHORIZED(401, "Not authorized"),
    FORBIDDEN(403, "Forbidden"),
    TOO_MANY_REQUESTS(429, "Too many requests"),
    NO_DATA(550, "No data available"),
    GENERIC(-1, "Unknown error"),
    INTERNET(-2, "Internet connection error");
}