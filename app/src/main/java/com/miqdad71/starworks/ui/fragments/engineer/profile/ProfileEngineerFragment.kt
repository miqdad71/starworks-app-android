package com.miqdad71.starworks.ui.fragments.engineer.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayoutManager
import com.miqdad71.starworks.R
import com.miqdad71.starworks.data.model.account.AccountModel
import com.miqdad71.starworks.data.model.engineer.EngineerModel
import com.miqdad71.starworks.data.model.skill.SkillModel
import com.miqdad71.starworks.data.remote.ApiClient
import com.miqdad71.starworks.data.remote.ApiClient.Companion.BASE_URL_IMAGE
import com.miqdad71.starworks.databinding.FragmentProfileEngineerBinding
import com.miqdad71.starworks.serviceapi.AccountAPI
import com.miqdad71.starworks.serviceapi.EngineerAPI
import com.miqdad71.starworks.serviceapi.SkillAPI
import com.miqdad71.starworks.ui.activity.main.company.SettingsActivity
import com.miqdad71.starworks.ui.activity.main.engineer.img_profile.ImageProfileEngineer
import com.miqdad71.starworks.ui.activity.skill.EditSkillActivity
import com.miqdad71.starworks.ui.activity.skill.SkillActivity
import com.miqdad71.starworks.ui.adapter.skill.ProfileSkillAdapter
import com.miqdad71.starworks.ui.fragments.engineer.profile.experience.ExperienceEngineerFragment
import com.miqdad71.starworks.ui.fragments.engineer.profile.portfolio.PortfolioEngineerFragment
import com.miqdad71.starworks.util.SharedPreference
import com.miqdad71.starworks.util.ViewPagerAdapter
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_engineer, container, false)
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = SharedPreference(requireActivity())
        userDetail = sharedPref.getAccountUser()
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

        serviceAccount = ApiClient.getApiClient(requireActivity()).create(AccountAPI::class.java)
        serviceEngineer = ApiClient.getApiClient(requireActivity()).create(EngineerAPI::class.java)
        serviceSkill = ApiClient.getApiClient(requireActivity()).create(SkillAPI::class.java)

        binding.ivAddSkill.setOnClickListener {
            val intent = Intent(context, SkillActivity::class.java)
            startActivity(intent)
        }
        binding.ivImageProfile.setOnClickListener {
            val intent = Intent(activity, ImageProfileEngineer::class.java)
            startActivity(intent)
        }

        getAdapterPortExp()
        setToolbarActionBar()
        getDataAccount()
        getDataUser()
        getAdapterSkill()
        getAllSkill()
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
                startActivity(Intent(activity, SettingsActivity::class.java))
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

    private fun getAdapterPortExp(){
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        val adapterPager = ViewPagerAdapter(parentFragmentManager)

        adapterPager.addFrag(PortfolioEngineerFragment(), "Portfolio")
        adapterPager.addFrag(ExperienceEngineerFragment(), "Experience")
        binding.viewPager.adapter = adapterPager
    }

    private fun getAdapterSkill() {
        binding.rvSkill.layoutManager = FlexboxLayoutManager(context)

        val adapter = ProfileSkillAdapter()
        binding.rvSkill.adapter = adapter

        adapter.setOnItemClickCallback(object : ProfileSkillAdapter.OnItemClickCallback {
            override fun onItemClick(data: SkillModel) {
                val intent = Intent(activity, EditSkillActivity::class.java)
                intent.putExtra("sk_id", data.sk_id)
                intent.putExtra("sk_skill_name", data.sk_skill_name)
                startActivityForResult(intent, 200)
            }
        })
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
                Glide.with(this@ProfileEngineerFragment)
                    .load(BASE_URL_IMAGE + dataFromResult.enProfile)
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
        getDataUser()
        getDataAccount()
        getAdapterSkill()
    }

}
