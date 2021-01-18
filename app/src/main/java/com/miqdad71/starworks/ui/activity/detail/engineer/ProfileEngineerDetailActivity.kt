package com.miqdad71.starworks.ui.activity.detail.engineer

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.flexbox.FlexboxLayoutManager
import com.miqdad71.starworks.R
import com.miqdad71.starworks.data.model.engineer.EngineerModel
import com.miqdad71.starworks.data.model.skill.SkillModel
import com.miqdad71.starworks.data.model.skill.SkillResponse
import com.miqdad71.starworks.data.remote.ApiClient
import com.miqdad71.starworks.databinding.ActivityProfileDetailBinding
import com.miqdad71.starworks.serviceapi.SkillAPI
import com.miqdad71.starworks.ui.activity.detail.company.HireCompanyActivity
import com.miqdad71.starworks.ui.adapter.skill.ProfileSkillAdapter
import com.miqdad71.starworks.ui.fragments.engineer.profile.experience.ExperienceEngineerFragment
import com.miqdad71.starworks.ui.fragments.engineer.profile.portfolio.PortfolioEngineerFragment
import com.miqdad71.starworks.util.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_profile_detail.*
import kotlinx.coroutines.*

class ProfileEngineerDetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityProfileDetailBinding
    private lateinit var coroutineScope: CoroutineScope

    private var enId: Int? = 0
    private var prId: Int? = 0
    private var exId: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_detail)
        super.onCreate(savedInstanceState)

        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        enId = intent.getIntExtra("en_id", 0)
        prId = intent.getIntExtra("pr_id", 0)
        exId = intent.getIntExtra("ex_id", 0)


        setToolbarActionBar()
        setEngineer()

        initViewPager()
        setSkill()
        setSkillRecyclerView()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_hire -> {
                val intent = Intent(this@ProfileEngineerDetailActivity, HireCompanyActivity::class.java)
                intent.putExtra("en_id", enId)
                startActivity(intent)
            }
        }
    }

    private fun setToolbarActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Profile Detail"
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setEngineer() {
        binding.engineer = EngineerModel(
            enId = enId!!,
            acId = intent.getIntExtra("ac_id", 0),
            acName = intent.getStringExtra("ac_name")!!,
            enJobTitle = intent.getStringExtra("en_job_title"),
            enJobType = intent.getStringExtra("en_job_type"),
            enDomicile = intent.getStringExtra("en_domicile"),
            enDescription = intent.getStringExtra("en_description")
        )

        binding.imageUrl = ApiClient.BASE_URL_IMAGE + intent.getStringExtra("en_profile")
    }

    private fun initViewPager() {
        binding.tabLayout.setupWithViewPager(view_pager)
        val adapter = ViewPagerAdapter(supportFragmentManager)

        adapter.addFrag(PortfolioEngineerFragment(), "Portfolio")
        adapter.addFrag(ExperienceEngineerFragment(), "Experience")
        binding.viewPager.adapter = adapter
    }

    private fun setSkillRecyclerView() {
        binding.rvSkill.layoutManager = FlexboxLayoutManager(this@ProfileEngineerDetailActivity)

        val adapter = ProfileSkillAdapter()
        binding.rvSkill.adapter = adapter
    }


    private fun setSkill() {
        val api = ApiClient.getApiClient(this).create(SkillAPI::class.java)

        coroutineScope.launch {
            val response = withContext(Dispatchers.Main) {
                try {
                    api.getAllSkill(intent.getIntExtra("en_id", 0))
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is SkillResponse) {
                val list = response.data.map {
                    SkillModel(
                        sk_id = it.sk_id,
                        sk_skill_name = it.skSkillName
                    )
                }

                (binding.rvSkill.adapter as ProfileSkillAdapter).addList(list)
            }
        }
    }
}
