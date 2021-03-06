package com.miqdad71.starworks.ui.fragments.engineer.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.FragmentSearchEngineerBinding

class SearchEngineerFragment : Fragment() {
    private lateinit var rootView: View
    private lateinit var binding: FragmentSearchEngineerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

//      binding fragments
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_search_engineer, container, false)
        return binding.root

    }
}