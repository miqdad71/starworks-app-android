package com.miqdad71.starworks.ui.fragments.engineer.profile.experience

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miqdad71.starworks.R
import com.miqdad71.starworks.data.model.experience.ExperienceModel
import com.miqdad71.starworks.data.remote.ApiClient
import com.miqdad71.starworks.databinding.FragmentExperienceBinding
import com.miqdad71.starworks.serviceapi.ExperienceAPI
import com.miqdad71.starworks.ui.activity.experience.ExperienceActivity
import com.miqdad71.starworks.ui.adapter.experience.ExperienceEngineerAdapter
import com.miqdad71.starworks.ui.fragments.engineer.profile.portfolio.PortfolioEngineerFragment.Companion.INTENT_EDIT
import com.miqdad71.starworks.util.SharedPreference
import kotlinx.coroutines.*

class ExperienceEngineerFragment : Fragment() {

    private lateinit var binding: FragmentExperienceBinding
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var sharedPref: SharedPreference
    private lateinit var service: ExperienceAPI

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_experience, container, false)

        sharedPref = SharedPreference(requireContext())
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = ApiClient.getApiClient(requireActivity()).create(ExperienceAPI::class.java)

        setupExperienceRecyclerView()
        getAllExperience()

        return binding.root
    }

    private fun setupExperienceRecyclerView() {
        binding.rvExperience.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        val adapter = ExperienceEngineerAdapter()
        binding.rvExperience.adapter = adapter

        adapter.setOnItemClickCallback(object : ExperienceEngineerAdapter.OnItemClickCallback {
            override fun onItemClick(data: ExperienceModel) {
                val intent = Intent(activity, ExperienceActivity::class.java)
                intent.putExtra("en_id", data.en_id)
                intent.putExtra("ex_id", data.ex_id)
                intent.putExtra("ex_position", data.ex_position)
                intent.putExtra("ex_company", data.ex_company)
                intent.putExtra("ex_start", data.ex_start!!.split('T')[0])
                intent.putExtra("ex_end", data.ex_end!!.split('T')[0])
                intent.putExtra("ex_description", data.ex_description)
                startActivityForResult(intent, INTENT_EDIT)
            }
        })
    }

    private fun getAllExperience() {
        coroutineScope.launch {
            try {
                val resultData = service.getAllExperience(sharedPref.getIdEngineer())
                val dataFromResult = resultData.data

                val list = dataFromResult.map {
                    ExperienceModel(
                        ex_id = it.exId,
                        en_id = it.enId,
                        ex_position = it.exPosition,
                        ex_company = it.exCompany,
                        ex_start = it.exStart.split('T')[0],
                        ex_end = it.exEnd.split('T')[0],
                        ex_description = it.exDescription
                        )
                }

                (binding.rvExperience.adapter as ExperienceEngineerAdapter).addList(list)

            } catch (e: Throwable) {
                Log.d("message", e.toString())
            }
        }
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }
}