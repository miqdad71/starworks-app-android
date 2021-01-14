package com.miqdad71.starworks.ui.fragments.company.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.miqdad71.starworks.R
import com.miqdad71.starworks.data.model.account.AccountModel
import com.miqdad71.starworks.data.model.company.CompanyModel
import com.miqdad71.starworks.data.remote.ApiClient
import com.miqdad71.starworks.databinding.FragmentProfileCompanyBinding
import com.miqdad71.starworks.serviceapi.AccountAPI
import com.miqdad71.starworks.serviceapi.CompanyAPI
import com.miqdad71.starworks.util.SharedPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ProfileCompanyFragment : Fragment() {
    private lateinit var binding: FragmentProfileCompanyBinding

    private lateinit var coroutineScope: CoroutineScope
    private lateinit var userDetail: HashMap<String, String>
    private lateinit var sharedPref: SharedPreference

    private lateinit var serviceAccount: AccountAPI
    private lateinit var serviceCompany: CompanyAPI

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_company, container, false)

        sharedPref = SharedPreference(requireContext())
        userDetail = sharedPref.getAccountUser()
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

        serviceAccount = ApiClient.getApiClient(requireActivity()).create(AccountAPI::class.java)
        serviceCompany = ApiClient.getApiClient(requireActivity()).create(CompanyAPI::class.java)

        binding.accountModel = AccountModel(
            acName = "${userDetail[SharedPreference.AC_NAME]}",
            acEmail = "${userDetail[SharedPreference.AC_EMAIL]}",
            acPhone = "${userDetail[SharedPreference.AC_PHONE]}"

        )

        binding.companyModel = CompanyModel(
            cn_company = "${userDetail[SharedPreference.CN_COMPANY]}",
            cn_position = "${userDetail[SharedPreference.CN_POSITION]}",
            cn_field = "${userDetail[SharedPreference.CN_FIELD]}",
            cn_city = "${userDetail[SharedPreference.CN_CITY]}",
            cn_description = "${userDetail[SharedPreference.CN_DESCRIPTION]}",
            cn_instagram = "${userDetail[SharedPreference.CN_INSTAGRAM]}",
            cn_linkedin = "${userDetail[SharedPreference.CN_LINKEDIN]}",
            cn_profile = "${userDetail[SharedPreference.CN_PROFILE]}"
        )

        getDataUser()

        return binding.root
    }

    private fun getDataUser() {
        coroutineScope.launch {
            try {
                val resultData = serviceCompany.getDetailCompany(sharedPref.getIdCompany())
                val dataFromResult = resultData.data[0]
                Log.d("msg", "$dataFromResult")

                binding.companyModel = CompanyModel(
                    cn_company = dataFromResult.cnCompany,
                    cn_position = dataFromResult.cnPosition,
                    cn_field = dataFromResult.cnField,
                    cn_city = dataFromResult.cnCity,
                    cn_description = dataFromResult.cnDescription,
                    cn_instagram = dataFromResult.cnInstagram,
                    cn_linkedin = dataFromResult.cnLinkedin,
                    cn_profile = dataFromResult.cnProfile
                )
            } catch (e: Throwable) {
                Log.d("message", e.toString())
            }
        }
    }

}
