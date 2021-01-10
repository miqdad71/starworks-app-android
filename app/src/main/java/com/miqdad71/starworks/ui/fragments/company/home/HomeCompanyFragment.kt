package com.miqdad71.starworks.ui.fragments.company.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miqdad71.starworks.R
import com.miqdad71.starworks.api.EngineerAPI
import com.miqdad71.starworks.data.model.account.AccountModel
import com.miqdad71.starworks.data.model.engineer.HomeEngineerModel
import com.miqdad71.starworks.databinding.FragmentHomeCompanyBinding
import com.miqdad71.starworks.data.remote.ApiClient
import com.miqdad71.starworks.ui.activity.detail.ProfileDetailActivity
import com.miqdad71.starworks.ui.adapter.company.HomeCompanyAdapter
import com.miqdad71.starworks.util.SharedPreference
import com.miqdad71.starworks.util.SharedPreference.Companion.AC_NAME
import kotlinx.coroutines.*

class HomeCompanyFragment : Fragment() {
    private lateinit var rootView: View
    private lateinit var binding: FragmentHomeCompanyBinding
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var api: EngineerAPI
    private lateinit var userDetail: HashMap<String, String>
    protected lateinit var sharedPref: SharedPreference

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_company, container, false)

        sharedPref = SharedPreference(requireContext())
        userDetail = sharedPref.getAccountUser()

        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        api = ApiClient.getApiClient(requireActivity()).create(EngineerAPI::class.java)
        binding.accountModel = AccountModel(acName = "Hai, ${userDetail[AC_NAME]}")

        getAllEngineer()
        return binding.root
    }


    private fun getAllEngineer() {
        val api = ApiClient.getApiClient(requireActivity()).create(EngineerAPI::class.java)

        coroutineScope.launch {
            try {
                val resultData = api.getAllEngineer()
                val dataFromResult = resultData.data
                //rv web
                binding.rvWeb.layoutManager = LinearLayoutManager(requireActivity().applicationContext, RecyclerView.HORIZONTAL, false)
                val adapter = HomeCompanyAdapter().apply { addList(dataFromResult) }
                binding.rvWeb.adapter = adapter
                adapter.setOnItemClickCallback(object: HomeCompanyAdapter.OnItemClickCallback {
                    override fun onItemClick(data: HomeEngineerModel) {
                        val intent = Intent(activity, ProfileDetailActivity::class.java)
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

                //rv android
                binding.rvAndroid.layoutManager = LinearLayoutManager(requireActivity().applicationContext, RecyclerView.HORIZONTAL, false)
                binding.rvAndroid.adapter = HomeCompanyAdapter().apply { addList(dataFromResult) }
            }catch (e: Throwable) {

            }
        }
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }
}
