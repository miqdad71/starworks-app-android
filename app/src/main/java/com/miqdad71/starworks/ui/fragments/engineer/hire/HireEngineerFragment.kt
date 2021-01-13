package com.miqdad71.starworks.ui.fragments.engineer.hire

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
import com.miqdad71.starworks.serviceapi.HireAPI
import com.miqdad71.starworks.data.model.hire.HireModel
import com.miqdad71.starworks.data.remote.ApiClient
import com.miqdad71.starworks.databinding.FragmentHireEngineerBinding
import com.miqdad71.starworks.ui.activity.detail.engineer.ProjectDetailActivity
import com.miqdad71.starworks.ui.adapter.engineer.EngineerProjectAdapter
import com.miqdad71.starworks.util.SharedPreference
import kotlinx.coroutines.*

class HireEngineerFragment : Fragment() {
    private lateinit var binding: FragmentHireEngineerBinding
    private lateinit var coroutineScope: CoroutineScope

    private lateinit var sharedPref: SharedPreference
    private lateinit var service: HireAPI

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hire_engineer, container, false)

        sharedPref = SharedPreference(requireActivity())
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = ApiClient.getApiClient(requireActivity()).create(HireAPI::class.java)

        binding.rvBiddingProject.layoutManager = LinearLayoutManager(requireActivity().applicationContext, RecyclerView.VERTICAL, false)
        val adapter = EngineerProjectAdapter()
        binding.rvBiddingProject.adapter = adapter
        adapter.setOnItemClickCallback(object : EngineerProjectAdapter.OnItemClickCallback {
            override fun onItemClick(data: HireModel) {
                val intent = Intent(activity, ProjectDetailActivity::class.java)
                intent.putExtra("hr_id", data.hrId)
                intent.putExtra("en_id", data.enId)
                intent.putExtra("pj_id", data.pjId)
                intent.putExtra("hr_price", data.hrPrice)
                intent.putExtra("hr_message", data.hrMessage)
                intent.putExtra("hr_status", data.hrStatus)
                intent.putExtra("hr_date_confirm", data.hrDateConfirm)
                intent.putExtra("pj_project_name", data.pjProjectName)
                intent.putExtra("pj_description", data.pjDescription)
                intent.putExtra("pj_deadline", data.pjDeadline)
                intent.putExtra("cn_company", data.cnCompany)
                intent.putExtra("cn_field", data.cnField)
                intent.putExtra("cn_city", data.cnCity)
                intent.putExtra("cn_profile", data.cnProfile)
                startActivity(intent)
            }
        })
        getHireEnId()
        return binding.root

    }

    private fun getHireEnId() {

        coroutineScope.launch {
            try {
                val resultData = service.getAllHire(sharedPref.getIdEngineer())
                val dataFromResult = resultData.data

                val list = dataFromResult.map {
                    HireModel(
                        hrId = it.hrId,
                        enId = it.enId,
                        pjId = it.pjId,
                        hrPrice = it.hrPrice,
                        hrMessage = it.hrMessage,
                        hrStatus = it.hrStatus,
                        hrDateConfirm = it.hrDateConfirm,
                        pjProjectName = it.pjProjectName,
                        pjDescription = it.pjDescription,
                        pjDeadline = it.pjDeadline,
                        cnCompany = it.cnCompany,
                        cnField = it.cnField,
                        cnCity = it.cnCity,
                        cnProfile = it.cnProfile
                    )
                }

                (binding.rvBiddingProject.adapter as EngineerProjectAdapter).addList(list)

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