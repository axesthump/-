package com.gif.app.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gif.app.presentation.ui.ChangePageListener
import com.gif.app.presentation.ui.PageFragment
import com.gif.app.presentation.ui.PageType

class PageAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    private val pages = listOf(PageFragment.newInstance(PageType.LATEST), PageFragment.newInstance(PageType.TOP), PageFragment.newInstance(PageType.HOT))

    override fun getItemCount(): Int = pages.size

    override fun createFragment(position: Int): Fragment = pages[position]

    fun nextGif() {
        (pages.find { it.isVisible } as ChangePageListener).onNextClick()
    }

    fun backGif() {
        (pages.find { it.isVisible } as ChangePageListener).onBackClick()
    }

}

