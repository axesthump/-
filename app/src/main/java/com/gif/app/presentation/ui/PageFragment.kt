package com.gif.app.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.gif.app.databinding.ItemPageBinding

class PageFragment: Fragment(), ChangePageListener, BlockBackButton {

    private lateinit var viewModel: PageViewModel
    private lateinit var viewBinding: ItemPageBinding
    private lateinit var pageType: PageType
    private lateinit var circularProgressDrawable: CircularProgressDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt(PAGE_TYPE_KEY)?.let {
            pageType = PageType.getType(it)
        }
    }

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
        circularProgressDrawable = initCircularProgressDrawable()
        viewModel = ViewModelProvider(this, PageViewModelFactory(pageType))[PageViewModel::class.java]
        viewModel.listenError()
        viewBinding.listenRetry()
        viewModel.getGifData()
        viewModel.loadGif()

    }

    override fun onStart() {
        super.onStart()
    }

    private fun initCircularProgressDrawable(): CircularProgressDrawable {
        return CircularProgressDrawable(requireContext()).apply {
            strokeWidth = 5f
            centerRadius = 30f
            start()
        }
    }

    private fun ItemPageBinding.listenRetry() {
        retry.setOnClickListener {
            viewModel.getGifData()
            viewModel.loadGif()
        }
    }


    private fun PageViewModel.loadGif() {
        liveData.observe(viewLifecycleOwner) {
            Glide
                .with(this@PageFragment)
                .load(it.url)
                .transform(CenterCrop())
                .placeholder(circularProgressDrawable)
                .into(viewBinding.img)

            viewBinding.descriptionText.text = it.description
        }
    }

    private fun PageViewModel.listenError() {
        errorLiveData.observe(viewLifecycleOwner) {
            if (it) {
                viewBinding.errorMessage.visibility = View.VISIBLE
                viewBinding.retry.visibility = View.VISIBLE
            } else {
                viewBinding.errorMessage.visibility = View.GONE
                viewBinding.retry.visibility = View.GONE
            }
        }
    }

    override fun onBackClick() {
        viewModel.prevGif()
    }

    override fun onNextClick() {
        viewModel.nextGif()
    }

    override fun canBlock(): Boolean = viewModel.canBlock()

    companion object {
        private const val PAGE_TYPE_KEY = "page_type_key"

        fun newInstance(type: PageType): PageFragment = PageFragment()
            .apply {
                arguments = Bundle().apply {
                    putInt(PAGE_TYPE_KEY, type.index)
                }
            }
    }

}