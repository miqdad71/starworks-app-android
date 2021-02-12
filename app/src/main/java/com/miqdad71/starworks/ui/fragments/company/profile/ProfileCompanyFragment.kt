package com.miqdad71.starworks.ui.fragments.company.profile

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile_company, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = SharedPreference(requireContext())
        userDetail = sharedPref.getAccountUser()
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

        serviceAccount = ApiClient.getApiClient(requireActivity()).create(AccountAPI::class.java)
        serviceCompany = ApiClient.getApiClient(requireActivity()).create(CompanyAPI::class.java)

        getDataAccount()
        getDataUser()
        setToolbarActionBar()
    }

    private fun setToolbarActionBar() {
        val tb = (activity as AppCompatActivity)

        tb.setSupportActionBar(binding.toolbar)
        tb.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        tb.supportActionBar?.title = ""
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_profile, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.miSetting -> {
                Log.d("Message : ", " Setting ")
                true
            }
            R.id.miLogout -> {
                sharedPref.accountLogout()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun getDataUser() {
        coroutineScope.launch {
            try {
                val resultData = serviceCompany.getDetailCompany(sharedPref.getIdAccount())
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
                Glide.with(this@ProfileCompanyFragment)
                    .load(ApiClient.BASE_URL_IMAGE + dataFromResult.cnProfile)
                    .placeholder(R.drawable.ic_backround_user).into(binding.ivImageProfile)

            } catch (e: Throwable) {
                Log.d("message", e.toString())
            }
        }
    }

    private fun getDataAccount() {
        coroutineScope.launch {
            try {
                val resultData = serviceAccount.detailAccount(sharedPref.getIdAccount())
                val dataFromResult = resultData.data[0]
                Log.d("msg", "$dataFromResult")

                binding.accountModel = AccountModel(
                    acName = dataFromResult.acName,
                    acId = dataFromResult.acId,
                    acEmail = dataFromResult.acEmail,
                    acPhone = dataFromResult.acPhone
                )
            } catch (e: Throwable) {
                Log.d("message", e.toString())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getDataUser()
        getDataAccount()
    }
}

