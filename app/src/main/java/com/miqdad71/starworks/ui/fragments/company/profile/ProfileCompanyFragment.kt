package com.miqdad71.starworks.ui.fragments.company.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.miqdad71.starworks.R
import com.miqdad71.starworks.data.model.account.AccountModel
import com.miqdad71.starworks.data.remote.ApiClient
import com.miqdad71.starworks.databinding.FragmentProfileCompanyBinding
import com.miqdad71.starworks.serviceapi.EngineerAPI
import com.miqdad71.starworks.util.SharedPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class ProfileCompanyFragment : Fragment() {
    private lateinit var binding: FragmentProfileCompanyBinding
    private lateinit var coroutineScope: CoroutineScope

    private lateinit var userDetail: HashMap<String, String>
    private lateinit var sharedPref: SharedPreference
    private lateinit var service: EngineerAPI
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_company, container, false)

        sharedPref = SharedPreference(requireContext())
        userDetail = sharedPref.getAccountUser()

        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = ApiClient.getApiClient(requireActivity()).create(EngineerAPI::class.java)
        binding.accountModel = AccountModel(acName = "Hai, ${userDetail[SharedPreference.AC_NAME]}")

        return binding.root

    }

}
