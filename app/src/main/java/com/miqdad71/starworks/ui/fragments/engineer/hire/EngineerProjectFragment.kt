package com.miqdad71.starworks.ui.fragments.engineer.hire

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.FragmentEngineerProjectBinding
import com.miqdad71.starworks.databinding.FragmentProfileEngineerBinding

class EngineerProjectFragment : Fragment() {

    private lateinit var rootView: View
    private lateinit var binding: FragmentEngineerProjectBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_engineer_project, container, false)
        return binding.root

    }


}