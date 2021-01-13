package com.miqdad71.starworks.ui.fragments.company.project

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
import com.miqdad71.starworks.data.model.project.ProjectModel
import com.miqdad71.starworks.data.remote.ApiClient
import com.miqdad71.starworks.databinding.FragmentProjectCompanyBinding
import com.miqdad71.starworks.serviceapi.ProjectAPI
import com.miqdad71.starworks.ui.activity.detail.engineer.ProjectDetailActivity
import com.miqdad71.starworks.ui.activity.main.company.AddProjectActivity
import com.miqdad71.starworks.ui.adapter.company.project.CompanyProjectAdapter
import com.miqdad71.starworks.util.SharedPreference
import kotlinx.coroutines.*

class ProjectCompanyFragment : Fragment() {

    private lateinit var binding: FragmentProjectCompanyBinding
    private lateinit var coroutineScope: CoroutineScope

    private lateinit var sharedPref: SharedPreference
    private lateinit var service: ProjectAPI

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_company, container, false)

        sharedPref = SharedPreference(requireContext())
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = ApiClient.getApiClient(requireActivity())
            .create(ProjectAPI::class.java)

        binding.btnCreateProject.setOnClickListener {
            val intent = Intent(activity, AddProjectActivity::class.java)
            startActivity(intent)
        }

        binding.rvCompanyProject.layoutManager = LinearLayoutManager(requireActivity().applicationContext,
            RecyclerView.VERTICAL,false)
        val adapter = CompanyProjectAdapter()
        binding.rvCompanyProject.adapter = adapter

        adapter.setOnItemClickCallback(object : CompanyProjectAdapter.OnItemClickCallback {
            override fun onItemClick(data: ProjectModel) {
                val intent = Intent(activity, ProjectDetailActivity::class.java)
                intent.putExtra("pj_id", data.pjId)
                intent.putExtra("cn_id", data.cnId)
                intent.putExtra("pj_project_name", data.pjProjectName)
                intent.putExtra("pj_description", data.pjDescription)
                intent.putExtra("pj_deadline", data.pjDeadline)
                intent.putExtra("pj_image", data.pjImage)
                startActivity(intent)
            }
        })

        getAllProject()

        return binding.root

    }


    private fun getAllProject() {

        coroutineScope.launch {
            try {
                val resultData = service.getAllProject(sharedPref.getIdCompany())
                val dataFromResult = resultData.data

                val list = dataFromResult.map {
                    ProjectModel(
                        pjId = it.pjId,
                        cnId = it.cnId,
                        pjProjectName = it.pjProjectName,
                        pjDescription = it.pjDescription,
                        pjDeadline = it.pjDeadline,
                        pjImage = it.pjImage
                    )
                }

                (binding.rvCompanyProject.adapter as CompanyProjectAdapter).addList(list)

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