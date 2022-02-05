package com.gif.app.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.gif.app.databinding.ItemPageBinding

class PageFragment: Fragment() {

    private lateinit var viewModel: PageViewModel
    private lateinit var viewBinding: ItemPageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = ItemPageBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, PageViewModelFactory(PageType.LATEST))[PageViewModel::class.java]
        viewModel.getGif()
        viewModel.liveData.observe(viewLifecycleOwner) {
            Glide
                .with(this)
                .load(it.url)
                .into(viewBinding.img)
            viewBinding.descriptionText.text = it.description
        }
    }

}