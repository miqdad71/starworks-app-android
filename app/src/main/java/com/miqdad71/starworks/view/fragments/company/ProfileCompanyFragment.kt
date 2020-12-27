package com.miqdad71.starworks.view.fragments.company

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.FragmentProfileEngineerBinding
import com.miqdad71.starworks.view.adapter.EngineerPagerAdapter
import com.miqdad71.starworks.view.webview.WebViewActivity

class ProfileCompanyFragment : Fragment(R.layout.fragment_profile_company), View.OnClickListener {
    private lateinit var binding: FragmentProfileEngineerBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

//      binding fragments
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_company, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = EngineerPagerAdapter( fragmentManager as FragmentManager)
        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.tvName.setOnClickListener(this)

    }

    override fun onClick(v: View?){

        when (v?.id) {
            R.id.tv_name -> {
                val intent = Intent(v.context, WebViewActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
