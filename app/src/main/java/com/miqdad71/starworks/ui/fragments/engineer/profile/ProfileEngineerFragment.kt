package com.miqdad71.starworks.ui.fragments.engineer.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.flexbox.FlexboxLayoutManager
import com.miqdad71.starworks.R
import com.miqdad71.starworks.data.model.account.AccountModel
import com.miqdad71.starworks.data.model.engineer.EngineerModel
import com.miqdad71.starworks.data.model.skill.SkillModel
import com.miqdad71.starworks.data.remote.ApiClient
import com.miqdad71.starworks.databinding.FragmentProfileEngineerBinding
import com.miqdad71.starworks.serviceapi.AccountAPI
import com.miqdad71.starworks.serviceapi.EngineerAPI
import com.miqdad71.starworks.serviceapi.SkillAPI
import com.miqdad71.starworks.ui.activity.skill.EditSkillActivity
import com.miqdad71.starworks.ui.activity.skill.SkillActivity
import com.miqdad71.starworks.ui.adapter.engineer.EngineerPagerAdapter
import com.miqdad71.starworks.ui.adapter.skill.ProfileSkillAdapter
import com.miqdad71.starworks.ui.webview.WebViewActivity
import com.miqdad71.starworks.util.SharedPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ProfileEngineerFragment : Fragment() {
    private lateinit var binding: FragmentProfileEngineerBinding

    private lateinit var coroutineScope: CoroutineScope
    private lateinit var userDetail: HashMap<String, String>
    private lateinit var sharedPref: SharedPreference

    private lateinit var serviceAccount: AccountAPI
    private lateinit var serviceEngineer: EngineerAPI
    private lateinit var serviceSkill: SkillAPI

    private lateinit var adapter: EngineerPagerAdapter

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_engineer, container, false)

        sharedPref = SharedPreference(requireActivity())
        userDetail = sharedPref.getAccountUser()
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

        serviceAccount = ApiClient.getApiClient(requireActivity()).create(AccountAPI::class.java)
        serviceEngineer = ApiClient.getApiClient(requireActivity()).create(EngineerAPI::class.java)
        serviceSkill = ApiClient.getApiClient(requireActivity()).create(SkillAPI::class.java)

        binding.tvGithub.setOnClickListener {
            val intent = Intent(context, WebViewActivity::class.java)
            startActivity(intent)
        }
        binding.ivAddSkill.setOnClickListener {
            val intent = Intent(context, SkillActivity::class.java)
            startActivity(intent)
        }

        binding.accountModel = AccountModel(
            acName = "${userDetail[SharedPreference.AC_NAME]}",
            acEmail = "${userDetail[SharedPreference.AC_EMAIL]}",
            acPhone = "${userDetail[SharedPreference.AC_PHONE]}"
        )

        binding.engineerModel = EngineerModel(
            enJobTitle = "${userDetail[SharedPreference.EN_JOB_TITLE]}",
            enJobType = "${userDetail[SharedPreference.EN_JOB_TYPE]}",
            enDomicile = "${userDetail[SharedPreference.EN_DOMICILE]}",
            enDescription = "${userDetail[SharedPreference.EN_DESCRIPTION]}"
        )
        setToolbarActionBar()
        getDataUser()

        binding.rvSkill.layoutManager = FlexboxLayoutManager(context)

        val adapter = ProfileSkillAdapter()
        binding.rvSkill.adapter = adapter

        adapter.setOnItemClickCallback(object: ProfileSkillAdapter.OnItemClickCallback {
            override fun onItemClick(data: SkillModel) {
                val intent = Intent(activity, EditSkillActivity::class.java)
                intent.putExtra("sk_id", data.sk_id)
                intent.putExtra("sk_skill_name", data.sk_skill_name)
                startActivityForResult(intent, 200)
            }
        })

        getAllSkill()


        return binding.root
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

        return when(item.itemId){
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
                val resultData = serviceEngineer.getDetailEngineer(sharedPref.getIdAccount())
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

        @Suppress("DEPRECATION")
        adapter = EngineerPagerAdapter(requireFragmentManager())
        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    private fun getAllSkill() {

        coroutineScope.launch {
            try {
                val resultData = serviceSkill.getAllSkill(sharedPref.getIdEngineer())
                val dataFromResult = resultData.data

                val list = dataFromResult.map {
                    SkillModel(
                        sk_id = it.sk_id,
                        sk_skill_name = it.skSkillName
                    )
                }

                (binding.rvSkill.adapter as ProfileSkillAdapter).addList(list)

            } catch (e: Throwable) {
                Log.d("message", e.toString())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getAllSkill()
    }

}
