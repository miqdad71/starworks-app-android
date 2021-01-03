package com.miqdad71.starworks.view.fragments.company

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
import com.miqdad71.starworks.api.EngineerAPI
import com.miqdad71.starworks.databinding.FragmentSearchCompanyBinding
import com.miqdad71.starworks.remote.ApiClient
import com.miqdad71.starworks.view.fragments.company.searchdata.SearchCompanyAdapter
import com.miqdad71.starworks.view.fragments.company.searchdata.SearchCompanyResponse
import com.miqdad71.starworks.view.fragments.company.searchdata.SearchEngineerModel
import kotlinx.coroutines.*

class SearchCompanyFragment : Fragment(R.layout.fragment_search_engineer) {
    private lateinit var rootView: View
    private lateinit var binding: FragmentSearchCompanyBinding
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var service: EngineerAPI

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_company, container, false)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = ApiClient.getApiClient(requireActivity().applicationContext).create(EngineerAPI::class.java)
        binding.rvSearchListProject.adapter = SearchCompanyAdapter()
        binding.rvSearchListProject.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        getAllEngineer()
        return binding.root

    }
    private fun getAllEngineer() {
        coroutineScope.launch {
            Log.d("android2", "Start: ${Thread.currentThread().name}")

            val result = withContext(Dispatchers.IO) {
                Log.d("android2", "CallApi: ${Thread.currentThread().name}")
                try {
                    service?.getAllEngineer()
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (result is SearchCompanyResponse) {
                Log.d("android2", result.toString())
                val list = result.data?.map {
                    SearchEngineerModel(
                        it.enId, it.acId, it.acName, it.enJobTitle,
                        it.enJobType, it.enDomicile, it.enDescription, it.enProfile
                    )
                }

                (binding.rvSearchListProject.adapter as SearchCompanyAdapter).addList(list)
            }

        }

    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }
}