package com.gif.app.data

import com.gif.app.data.networking.GifServiceApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object RetrofitObject {

    private const val BASE_URL = "http://developerslife.ru/"

    private val contentType = MediaType.get("application/json")

    private val json = Json { ignoreUnknownKeys = true }

    private val okHttpClient = OkHttpClient().newBuilder().build()
    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()

    val gifServiceApi: GifServiceApi
    get() = retrofit.create(GifServiceApi::class.java)
}