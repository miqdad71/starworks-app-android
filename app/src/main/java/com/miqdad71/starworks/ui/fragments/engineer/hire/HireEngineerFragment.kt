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
import com.miqdad71.starworks.api.HireAPI
import com.miqdad71.starworks.data.model.engineer.HireProjectModel
import com.miqdad71.starworks.data.remote.ApiClient
import com.miqdad71.starworks.databinding.FragmentHireEngineerBinding
import com.miqdad71.starworks.ui.activities.detail.ProjectDetailActivity
import com.miqdad71.starworks.ui.adapter.engineer.EngineerProjectAdapter
import kotlinx.coroutines.*

class HireEngineerFragment : Fragment() {

    private lateinit var rootView: View
    private lateinit var binding: FragmentHireEngineerBinding
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var service: HireAPI

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hire_engineer, container, false)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = ApiClient.getApiClient(requireActivity())
            .create(HireAPI::class.java)

        getHireEnId()
        return binding.root

    }

    private fun getHireEnId(){

        coroutineScope.launch {
            try {
                val resultData = service.getHireEnId()
                val dataFromResult = resultData.data

                binding.rvBiddingProject.layoutManager = LinearLayoutManager(requireActivity().applicationContext, RecyclerView.VERTICAL, false)
                val adapter = EngineerProjectAdapter().apply { addList(dataFromResult) }
                binding.rvBiddingProject.adapter = adapter
                adapter.setOnItemClickCallback(object : EngineerProjectAdapter.OnItemClickCallback{
                    override fun onItemClick(data: HireProjectModel) {
                        val intent = Intent(activity, ProjectDetailActivity::class.java)
                        startActivity(intent)
                    }
                })
            } catch (e: Throwable){
                Log.d("message", e.toString())
            }
        }
    }
    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }
}