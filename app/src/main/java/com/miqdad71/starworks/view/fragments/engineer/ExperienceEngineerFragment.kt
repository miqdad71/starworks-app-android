package com.miqdad71.starworks.view.fragments.engineer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.FragmentExperienceBinding
import com.miqdad71.starworks.view.adapter.ListExperienceRecycleViewAdapter
import com.miqdad71.starworks.view.model.Databases
import com.miqdad71.starworks.view.model.Experience

class ExperienceEngineerFragment : Fragment(R.layout.fragment_experience) {

    private lateinit var rootView: View
    private lateinit var binding: FragmentExperienceBinding
    private val listExperience = ArrayList<Experience>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//      binding fragments
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_experience, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listExperience.addAll(Databases.listExperiences)
        showRecycleList()
    }

    private fun showRecycleList() {
        binding.rvExperience.apply {
            layoutManager = LinearLayoutManager(view?.context)
            adapter = ListExperienceRecycleViewAdapter(listExperience)
        }
    }

}