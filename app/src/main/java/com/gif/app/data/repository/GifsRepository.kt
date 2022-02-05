package com.gif.app.data.repository

import com.gif.app.data.RetrofitObject
import com.gif.app.domain.model.GifsModel

class GifsRepository {
    suspend fun getHot(pageNumber: Int): GifsModel = RetrofitObject.gifServiceApi.getGifsHot(pageNumber)
    suspend fun getLatest(pageNumber: Int): GifsModel = RetrofitObject.gifServiceApi.getGifsLatest(pageNumber)
    suspend fun getTop(pageNumber: Int): GifsModel = RetrofitObject.gifServiceApi.getGifsTop(pageNumber)
}