package com.gif.app.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gif.app.presentation.ui.BlockBackButton
import com.gif.app.presentation.ui.ChangePageListener
import com.gif.app.presentation.ui.PageFragment
import com.gif.app.presentation.ui.PageType.LATEST
import com.gif.app.presentation.ui.PageType.TOP
import com.gif.app.presentation.ui.PageType.HOT

class PageAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    private val pages = listOf(
        PageFragment.newInstance(LATEST),
        PageFragment.newInstance(TOP),
        PageFragment.newInstance(HOT)
    )

    override fun getItemCount(): Int = pages.size

    override fun createFragment(position: Int): Fragment = pages[position]

    fun nextGif() {
        (pages.find { it.isVisible } as ChangePageListener).onNextClick()
    }

    fun backGif() {
        (pages.find { it.isVisible } as ChangePageListener).onBackClick()
    }

    fun canBlockButton(): Boolean = (pages.find { it.isVisible } as BlockBackButton).canBlock()


}

