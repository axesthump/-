package com.gif.app.domain

import com.gif.app.data.RepositoryObject
import com.gif.app.domain.model.GifsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetTopGifsUseCase {
    private val repository = RepositoryObject.repository

    suspend operator fun invoke(pageNumber: Int): GifsModel {
        return withContext(Dispatchers.IO) {
            repository.getTop(pageNumber)
        }
    }
}