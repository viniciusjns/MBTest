package com.vinicius.mbtest.features.exchanges.data.remote.model

import com.google.gson.annotations.SerializedName

data class IconResponse(
    @SerializedName("exchange_id") val exchangeId: String?,
    @SerializedName("url") val url: String?
)