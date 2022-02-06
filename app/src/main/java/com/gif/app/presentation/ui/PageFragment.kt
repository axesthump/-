package com.gif.app.presentation.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.gif.app.databinding.ItemPageBinding
import kotlin.properties.Delegates


class PageFragment: Fragment(), ChangePageListener {

    private lateinit var viewModel: PageViewModel
    private lateinit var viewBinding: ItemPageBinding
    private lateinit var pageType: PageType
    private lateinit var circularProgressDrawable: CircularProgressDrawable
    private lateinit var blockButtonListener: BlockButtonListener
    private var cornerDp by Delegates.notNull<Float>()

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        blockButtonListener = context as BlockButtonListener
        cornerDp = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, DP_SIZE,
            (blockButtonListener as Context).resources.displayMetrics
        )
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

    override fun onResume() {
        super.onResume()
        blockButtonListener.changeBlockBackState(viewModel.canBlock())
        blockButtonListener.changeBlockForwardState(false)
    }

    override fun onBackClick() {
        viewBinding.img.visibility = View.VISIBLE
        viewModel.prevGif()
        viewModel.errorLiveData.postValue(false)
        blockButtonListener.changeBlockBackState(viewModel.canBlock())
    }

    override fun onNextClick() {
        viewModel.nextGif()
        viewModel.errorLiveData.postValue(false)
        blockButtonListener.changeBlockBackState(viewModel.canBlock())
        blockButtonListener.changeBlockForwardState(true)
    }


    private fun initCircularProgressDrawable(): CircularProgressDrawable {
        return CircularProgressDrawable(requireContext()).apply {
            strokeWidth = CIRCULAR_STROKE_WIDTH
            centerRadius = CIRCULAR_CENTER_RADIUS
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
                .listener(object: RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        errorLiveData.postValue(true)
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        blockButtonListener.changeBlockForwardState(false)
                        return false
                    }

                })
                .into(viewBinding.img)

            viewBinding.descriptionText.text = it.description
        }
    }

    private fun PageViewModel.listenError() {
        errorLiveData.observe(viewLifecycleOwner) {
            if (it) {
                blockButtonListener.changeBlockForwardState(true)
                with(viewBinding) {
                    errorMessage.visibility = View.VISIBLE
                    retry.visibility = View.VISIBLE
                    img.visibility = View.INVISIBLE
                    page.radius = MINIMAL_RADIUS
                    descriptionText.visibility = View.GONE
                }
            } else {
                blockButtonListener.changeBlockForwardState(false)
                with(viewBinding) {
                    errorMessage.visibility = View.GONE
                    retry.visibility = View.GONE
                    img.visibility = View.VISIBLE
                    page.radius = cornerDp
                    descriptionText.visibility = View.VISIBLE
                }

            }
        }
    }

    companion object {
        private const val PAGE_TYPE_KEY = "page_type_key"
        private const val DP_SIZE = 30f
        private const val CIRCULAR_STROKE_WIDTH = 5f
        private const val CIRCULAR_CENTER_RADIUS = 30f
        private const val MINIMAL_RADIUS = 0f

        fun newInstance(type: PageType): PageFragment = PageFragment()
            .apply {
                arguments = Bundle().apply {
                    putInt(PAGE_TYPE_KEY, type.index)
                }
            }
    }

}