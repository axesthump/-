package com.gif.app.presentation.ui

enum class PageType(val index: Int) {
    LATEST(0),
    TOP(1),
    HOT(2), ;

    companion object {
        fun getType(index: Int) = values().first { it.index == index }
    }
}