package com.gif.app.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GifsModel(

    @SerialName("result")
    val gifs: List<GifModel>,

    @SerialName("totalCount")
    val count: Int
)