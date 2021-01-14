package com.miqdad71.starworks.ui.fragments.engineer.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.miqdad71.starworks.R
import com.miqdad71.starworks.data.model.account.AccountModel
import com.miqdad71.starworks.data.model.engineer.EngineerModel
import com.miqdad71.starworks.data.remote.ApiClient
import com.miqdad71.starworks.databinding.FragmentProfileEngineerBinding
import com.miqdad71.starworks.serviceapi.AccountAPI
import com.miqdad71.starworks.serviceapi.EngineerAPI
import com.miqdad71.starworks.serviceapi.SkillAPI
import com.miqdad71.starworks.ui.activity.skill.SkillActivity
import com.miqdad71.starworks.ui.adapter.engineer.EngineerPagerAdapter
import com.miqdad71.starworks.ui.webview.WebViewActivity
import com.miqdad71.starworks.util.SharedPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ProfileEngineerFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentProfileEngineerBinding

    private lateinit var coroutineScope: CoroutineScope
    private lateinit var userDetail: HashMap<String, String>
    private lateinit var sharedPref: SharedPreference

    private lateinit var serviceAccount: AccountAPI
    private lateinit var serviceEngineer: EngineerAPI
    private lateinit var serviceSkill: SkillAPI

    private lateinit var adapter: EngineerPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_engineer, container, false)

        sharedPref = SharedPreference(requireActivity())
        userDetail = sharedPref.getAccountUser()
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

        serviceAccount = ApiClient.getApiClient(requireActivity()).create(AccountAPI::class.java)
        serviceEngineer = ApiClient.getApiClient(requireActivity()).create(EngineerAPI::class.java)
        serviceSkill = ApiClient.getApiClient(requireActivity()).create(SkillAPI::class.java)

        binding.accountModel = AccountModel(
            acName = "${userDetail[SharedPreference.AC_NAME]}",
            acEmail = "${userDetail[SharedPreference.AC_EMAIL]}"
        )

        binding.engineerModel = EngineerModel(
            enJobTitle = "${userDetail[SharedPreference.EN_JOB_TITLE]}",
            enJobType = "${userDetail[SharedPreference.EN_JOB_TYPE]}",
            enDomicile = "${userDetail[SharedPreference.EN_DOMICILE]}",
            enDescription = "${userDetail[SharedPreference.EN_DESCRIPTION]}"
        )

        getDataUser()

        return binding.root
    }

    private fun getDataUser() {
        coroutineScope.launch {
            try {
                val resultData = serviceEngineer.getDetailEngineer(sharedPref.getIdEngineer())
                val dataFromResult = resultData.data[0]
                Log.d("msg", "$dataFromResult")

                binding.engineerModel = EngineerModel(
                    enJobTitle = dataFromResult.enJobTitle,
                    enJobType = dataFromResult.enJobType,
                    enDomicile = dataFromResult.enDomicile,
                    enDescription = dataFromResult.enDescription
                )

            } catch (e: Throwable) {
                Log.d("message", e.toString())
            }
        }


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvGithub.setOnClickListener(this)
        binding.ivAddSkill.setOnClickListener(this)


        @Suppress("DEPRECATION")
        adapter = EngineerPagerAdapter(requireFragmentManager())
        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    override fun onResume() {
        super.onResume()
        @Suppress("DEPRECATION")
        adapter = EngineerPagerAdapter(requireFragmentManager())
        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.tv_github -> {
                val intent = Intent(v.context, WebViewActivity::class.java)
                startActivity(intent)
            }
            R.id.iv_add_skill -> {
                val intent = Intent(v.context, SkillActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
