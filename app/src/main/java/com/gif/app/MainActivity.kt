package com.gif.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import com.gif.app.databinding.ActivityMainBinding
import com.gif.app.presentation.adapter.PageAdapter
import com.gif.app.presentation.ui.BlockButtonListener
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity(), BlockButtonListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        with(binding) {
            setContentView(root)
            backGif.setClickable(false)
            val adapter = PageAdapter(this@MainActivity)
            viewPager.adapter = adapter
            TabLayoutMediator(tabLayout, viewPager) {
                tab, position -> tab.text = resources.getStringArray(R.array.pages_name)[position]
            }.attach()
            backGif.setOnClickListener {
                adapter.backGif()
            }
            forwardGif.setOnClickListener {
                adapter.nextGif()
            }
        }

    }

    override fun changeBlockBackState(isBlocked: Boolean) {
        if (isBlocked) {
            binding.backGif.setClickable(false)
            binding.backGif.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.disable_back_button, null))
        } else {
            binding.backGif.setClickable(true)
            binding.backGif.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.active_back_button, null))

        }
    }

    override fun changeBlockForwardState(isBlocked: Boolean) {
        if (isBlocked) {
            binding.forwardGif.setClickable(false)
            binding.forwardGif.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.disable_forward_button, null))

        } else {
            binding.forwardGif.setClickable(true)
            binding.forwardGif.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.active_forward_button, null))

        }
    }
}