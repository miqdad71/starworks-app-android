package com.miqdad71.starworks.view.fragments.company

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.FragmentHireCompanyBinding
import com.miqdad71.starworks.databinding.FragmentHireEngineerBinding

class HireCompanyFragment : Fragment() {

    private lateinit var rootView: View
    private lateinit var binding: FragmentHireCompanyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)

//      binding fragments
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_hire_company, container, false)
        return binding.root

    }


}