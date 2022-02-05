package com.gif.app.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gif.app.presentation.ui.PageFragment

class PageAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    private val pages = listOf(PageFragment(), PageFragment(), PageFragment())

    override fun getItemCount(): Int = pages.size

    override fun createFragment(position: Int): Fragment = pages[position]


}

