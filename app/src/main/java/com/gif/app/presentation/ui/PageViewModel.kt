package com.gif.app.presentation.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gif.app.domain.UseCaseObject
import com.gif.app.domain.model.GifModel
import kotlinx.coroutines.launch
import com.gif.app.presentation.ui.PageType.TOP
import com.gif.app.presentation.ui.PageType.HOT
import com.gif.app.presentation.ui.PageType.LATEST

class PageViewModel(private val type: PageType): ViewModel() {

    private val gifs = mutableListOf<GifModel>()
    private var gifPointer = 0
    private var pageCount = 0

    val liveData = MutableLiveData<GifModel>()
    private val badValue = GifModel(-1, "", "")
    private val emptyValue = GifModel(-2, "", "")

    fun prevGif() {
        if (gifPointer == 0)
            liveData.postValue(gifs[gifPointer])
        else {
            --gifPointer
            liveData.postValue(gifs[gifPointer])
        }
    }

    fun nextGif() {
        if (gifPointer + 1 == gifs.size)
            getGif()
        else {
            ++gifPointer
            liveData.postValue(gifs[gifPointer])
        }
    }

    fun getGif() {
        viewModelScope.launch {
            kotlin
                .runCatching {
                    when(type) {
                        TOP -> UseCaseObject.getTopGifsUseCase.invoke(pageCount)
                        HOT -> UseCaseObject.getHotGifsUseCase.invoke(pageCount)
                        LATEST -> UseCaseObject.getLatestGifsUseCase.invoke(pageCount)
                    }
                }
                .onSuccess {
                    gifs.addAll(it.gifs)
                    if (it.count == 0) {
                        liveData.postValue(emptyValue)
                    } else {
                        liveData.postValue(gifs[gifPointer])
                    }
                }
                .onFailure {
                    liveData.postValue(badValue)
                }
        }
    }


}