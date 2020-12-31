package com.miqdad71.starworks.view.fragments.company

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.FragmentProfileCompanyBinding

class ProfileCompanyFragment : Fragment(){
    private lateinit var binding: FragmentProfileCompanyBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

//      binding fragments
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_company, container, false)
        return binding.root


    }

}
