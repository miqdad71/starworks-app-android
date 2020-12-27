package com.miqdad71.starworks.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.FragmentExperienceBinding
import com.miqdad71.starworks.databinding.FragmentHomeBinding

class ExperienceFragment : Fragment(R.layout.fragment_experience) {

    private lateinit var rootView: View
    private lateinit var binding: FragmentExperienceBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return super.onCreateView(inflater, container, savedInstanceState)

//      binding fragments
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_experience, container, false)
        return binding.root

    }


}