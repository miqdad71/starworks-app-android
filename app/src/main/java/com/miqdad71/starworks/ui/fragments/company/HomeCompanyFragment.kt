package com.miqdad71.starworks.ui.fragments.company

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
import com.miqdad71.starworks.databinding.FragmentHomeCompanyBinding
import com.miqdad71.starworks.data.remote.ApiClient
import com.miqdad71.starworks.ui.fragments.company.homegetdata.*
//import com.miqdad71.starworks.view.detail_profile.ProfileDetailActivity
import kotlinx.coroutines.*


class HomeCompanyFragment : Fragment() {
    private lateinit var rootView: View
    private lateinit var binding: FragmentHomeCompanyBinding
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var service: EngineerAPI

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        super.onCreateView(inflater, container, savedInstanceState)

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home_company, container, false)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = ApiClient.getApiClient(requireActivity())
            .create(EngineerAPI::class.java)


        getAllEngineer()
        return binding.root

    }

    private fun getAllEngineer() {

        // other way
        coroutineScope.launch {
            try {
                val resultData = service.getAllEngineer()
                val dataFromResult = resultData.data
                //rv web
                binding.rvWeb.layoutManager = LinearLayoutManager(requireActivity().applicationContext, RecyclerView.HORIZONTAL, false)
                binding.rvWeb.adapter = HomeCompanyAdapter().apply { addList(dataFromResult) }

                //rv android
                binding.rvAndroid.layoutManager = LinearLayoutManager(requireActivity().applicationContext, RecyclerView.HORIZONTAL, false)
                binding.rvAndroid.adapter = HomeCompanyAdapter().apply { addList(dataFromResult) }
                Log.d("qwerty0", dataFromResult.toString())
            }catch (e: Throwable) {
                Log.d("qwerty0error", e.toString())
            }
        }

//        coroutineScope.launch {
//            Log.d("android2", "Start: ${Thread.currentThread().name}")
//
//            val result = withContext(Dispatchers.IO) {
//                Log.d("android2", "CallApi: ${Thread.currentThread().name}")
//                try {
//                    service?.getAllEngineer()
//
//                } catch (e: Throwable) {
//                    e.printStackTrace()
//                    Log.e("errorcatch", e.toString())
//                }
//            }
//
//            if (result is HomeCompanyResponse) {
//                Log.d("android2", result.toString())
//                val list = result.data.map {
//                    HomeEngineerModel(
//                        it.enId, it.acId, it.acName, it.enJobTitle,
//                        it.enJobType, it.enDomicile, it.enDescription, it.enProfile
//                    )
//                }
//                binding.rvWeb.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
//                (binding.rvWeb.adapter as HomeCompanyAdapter).addList(list)
//
//                Log.d("qwerty1", "data $list")
//
//            }
//
//            Log.d("qwerty3", service.toString())
//            Log.d("qwerty2", result.toString())
//
//        }

    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }
}
