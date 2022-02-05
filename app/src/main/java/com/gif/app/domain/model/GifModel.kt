package com.gif.app.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GifModel(
    @SerialName("id")
    val id: Int,

    @SerialName("description")
    val description: String,

    @SerialName("gifURL")
    val url: String,
)