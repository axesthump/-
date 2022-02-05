package com.gif.app.data

import com.gif.app.data.networking.GifServiceApi
import com.google.gson.GsonBuilder
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObject {

    private const val BASE_URL = "http://developerslife.ru/"

    private val okHttpClient = OkHttpClient().newBuilder().build()
    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()))
        .build()

    val gifServiceApi: GifServiceApi
    get() = retrofit.create(GifServiceApi::class.java)
}