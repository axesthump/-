package com.gif.app.data.networking

import com.gif.app.domain.model.GifsModel
import retrofit2.http.GET
import retrofit2.http.Path

interface GifServiceApi {

    @GET("latest/{pageNumber}?json=true")
    suspend fun getGifsLatest(@Path("pageNumber") pageNumber: Int): GifsModel

    @GET("hot/{pageNumber}?json=true")
    suspend fun getGifsHot(@Path("pageNumber") pageNumber: Int): GifsModel

    @GET("top/{pageNumber}?json=true")
    suspend fun getGifsTop(@Path("pageNumber") pageNumber: Int): GifsModel

}