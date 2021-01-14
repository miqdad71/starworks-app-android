package com.miqdad71.starworks.ui.fragments.company.hire

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miqdad71.starworks.R
import com.miqdad71.starworks.data.model.hire.HireModel
import com.miqdad71.starworks.data.remote.ApiClient
import com.miqdad71.starworks.databinding.FragmentHireCompanyBinding
import com.miqdad71.starworks.serviceapi.HireAPI
import com.miqdad71.starworks.ui.adapter.company.hire.CompanyHireAdapter
import com.miqdad71.starworks.util.SharedPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HireCompanyFragment : Fragment() {

    private lateinit var binding: FragmentHireCompanyBinding
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var sharedPref: SharedPreference
    private lateinit var userDetail: HashMap<String, String>
    private lateinit var service: HireAPI

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hire_company, container, false)

        sharedPref = SharedPreference(requireActivity())
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = ApiClient.getApiClient(requireActivity()).create(HireAPI::class.java)

        userDetail = sharedPref.getAccountUser()

        setToolbarActionBar()

        binding.rvHireCompany.layoutManager = LinearLayoutManager(requireActivity().applicationContext, RecyclerView.VERTICAL, false)
        val adapter = CompanyHireAdapter()
        binding.rvHireCompany.adapter = adapter

        getHireCnId()
        return binding.root
    }

    private fun getHireCnId() {

        coroutineScope.launch {
            try {
                val resultData = service.getAllHireCompany(sharedPref.getIdCompany())
                val dataFromResult = resultData.data

                val list = dataFromResult.map {
                    HireModel(
                        hrId = it.hrId,
                        enId = it.enId,
                        pjId = it.pjId,
                        acName = it.acName,
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

                (binding.rvHireCompany.adapter as CompanyHireAdapter).addList(list)

            } catch (e: Throwable) {
                Log.d("message", e.toString())
            }
        }
    }

    private fun setToolbarActionBar() {
        val tb = (activity as AppCompatActivity)

        tb.setSupportActionBar(binding.toolbar)
        tb.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        tb.supportActionBar?.title = "Hiring Project"
    }

}