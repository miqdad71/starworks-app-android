package com.miqdad71.starworks.ui.fragments.company.search

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
import com.miqdad71.starworks.data.model.engineer.EngineerModel
import com.miqdad71.starworks.data.remote.ApiClient
import com.miqdad71.starworks.databinding.FragmentSearchCompanyBinding
import com.miqdad71.starworks.serviceapi.EngineerAPI
import com.miqdad71.starworks.ui.activity.detail.profile.ProfileEngineerDetailActivity
import com.miqdad71.starworks.ui.adapter.company.search.SearchCompanyAdapter
import com.miqdad71.starworks.util.SharedPreference
import kotlinx.coroutines.*

class SearchCompanyFragment : Fragment() {
    private lateinit var binding: FragmentSearchCompanyBinding
    private lateinit var coroutineScope: CoroutineScope

    private lateinit var userDetail: HashMap<String, String>
    private lateinit var sharedPref: SharedPreference
    private lateinit var service: EngineerAPI


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_company, container, false)
        super.onCreateView(inflater, container, savedInstanceState)

        sharedPref = SharedPreference(requireContext())
        userDetail = sharedPref.getAccountUser()

        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = ApiClient.getApiClient(requireActivity()).create(EngineerAPI::class.java)

        binding.rvSearchListProject.layoutManager = LinearLayoutManager(
            requireActivity().applicationContext,
            RecyclerView.VERTICAL, false
        )
        val adapter = SearchCompanyAdapter()
        binding.rvSearchListProject.adapter = adapter

        adapter.setOnItemClickCallback(object : SearchCompanyAdapter.OnItemClickCallback {
            override fun onItemClick(data: EngineerModel) {
                val intent = Intent(activity, ProfileEngineerDetailActivity::class.java)
                intent.putExtra("en_id", data.enId)
                intent.putExtra("ac_id", data.acId)
                intent.putExtra("ac_name", data.acName)
                intent.putExtra("en_job_title", data.enJobTitle)
                intent.putExtra("en_domicile", data.enDomicile)
                intent.putExtra("en_job_type", data.enJobType)
                intent.putExtra("en_description", data.enDescription)
                intent.putExtra("en_profile", data.enProfile)
                startActivity(intent)
            }
        })
        getAllEngineer()
        return binding.root
    }

    private fun getAllEngineer() {

        coroutineScope.launch {
            try {
                val resultData = service.getAllEngineer()
                val dataFromResult = resultData.data

                val list = dataFromResult.map {
                    EngineerModel(
                        enId = it.enId,
                        acId = it.acId,
                        acName = it.acName,
                        enJobTitle = it.enJobTitle,
                        enJobType = it.enJobType,
                        enDomicile = it.enDomicile,
                        enDescription = it.enDescription,
                        enProfile = it.enProfile
                    )
                }

                (binding.rvSearchListProject.adapter as SearchCompanyAdapter).addList(list)

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