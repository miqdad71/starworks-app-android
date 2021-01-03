package com.miqdad71.starworks.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.ActivityTabLayoutBinding

class EngineerLayoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTabLayoutBinding
    private lateinit var pagerAdapter: EngineerPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tab_layout)

        pagerAdapter = EngineerPagerAdapter(supportFragmentManager)
        binding.viewPager.adapter = pagerAdapter

        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }
}