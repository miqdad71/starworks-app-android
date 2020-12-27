package com.miqdad71.starworks.view.fragments.engineer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.FragmentProfileEngineerBinding
import com.miqdad71.starworks.view.adapter.EngineerPagerAdapter
import com.miqdad71.starworks.view.webview.WebViewActivity

class ProfileEngineerFragment : Fragment(R.layout.fragment_profile_engineer), View.OnClickListener {
    private lateinit var binding: FragmentProfileEngineerBinding
    private lateinit var adapter: EngineerPagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

//      binding fragments
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_engineer, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = EngineerPagerAdapter( requireFragmentManager())

        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.tvGithub.setOnClickListener(this)

    }

    override fun onResume() {
        super.onResume()
        adapter = EngineerPagerAdapter( requireFragmentManager())
        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    override fun onClick(v: View?){

        when (v?.id) {
            R.id.tv_github -> {
                val intent = Intent(v.context, WebViewActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
