package com.miqdad71.starworks.ui.fragments.engineer.hire

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.FragmentHireEngineerBinding
import com.miqdad71.starworks.ui.adapter.EngineerPagerAdapter
import com.miqdad71.starworks.ui.adapter.HireEngineerPagerAdapter

class HireEngineerFragment : Fragment() {

    private lateinit var rootView: View
    private lateinit var binding: FragmentHireEngineerBinding
    private lateinit var pagerAdapter: HireEngineerPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

//      binding fragments
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_hire_engineer, container, false)

        pagerAdapter = HireEngineerPagerAdapter(requireActivity().supportFragmentManager)
        binding.vpHireEngineer.adapter = pagerAdapter
        binding.tlHireEngineer.setupWithViewPager(binding.vpHireEngineer)

        return binding.root

    }

    override fun onResume() {
        super.onResume()
        pagerAdapter = HireEngineerPagerAdapter(requireActivity().supportFragmentManager)
        binding.vpHireEngineer.adapter = pagerAdapter
        binding.tlHireEngineer.setupWithViewPager(binding.vpHireEngineer)
    }
}