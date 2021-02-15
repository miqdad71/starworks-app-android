package com.miqdad71.starworks.ui.activity.detail.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.flexbox.FlexboxLayoutManager
import com.miqdad71.starworks.R
import com.miqdad71.starworks.data.model.account.AccountModel
import com.miqdad71.starworks.data.model.account.AccountResponse
import com.miqdad71.starworks.data.model.engineer.EngineerModel
import com.miqdad71.starworks.data.model.engineer.EngineerResponse
import com.miqdad71.starworks.data.model.skill.SkillModel
import com.miqdad71.starworks.databinding.ActivityProfileDetailBinding
import com.miqdad71.starworks.ui.activity.detail.company.HireCompanyActivity
import com.miqdad71.starworks.ui.activity.detail.profile.experience.DetailProfileExperienceFragment
import com.miqdad71.starworks.ui.activity.detail.profile.portfolio.DetailProfilePortfolioFragment
import com.miqdad71.starworks.ui.adapter.skill.ProfileSkillAdapter
import com.miqdad71.starworks.ui.base.BaseActivityCoroutine
import com.miqdad71.starworks.util.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_profile_detail.*
import kotlinx.coroutines.*

class ProfileEngineerDetailActivity : BaseActivityCoroutine<ActivityProfileDetailBinding>(), ProfileDetailContract.View, View.OnClickListener {
    private var presenter: ProfileDetailPresenter? = null
    private var enId: Int? = 0
    private var acId: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_profile_detail
        super.onCreate(savedInstanceState)

        enId = intent.getIntExtra("en_id", 0)
        acId = intent.getIntExtra("ac_id", 0)

        presenter = ProfileDetailPresenter(
            serviceAccount = createApi(this@ProfileEngineerDetailActivity),
            serviceEngineer = createApi(this@ProfileEngineerDetailActivity),
            serviceSkill = createApi(this@ProfileEngineerDetailActivity),
            serviceHire = createApi(this@ProfileEngineerDetailActivity)
        )

        if (sharedPref.getLevelUser() == 0) {
            binding.btnHire.visibility = View.GONE
        } else {
            binding.btnHire.visibility = View.VISIBLE
        }

        setToolbarActionBar()
        initViewPager()
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

    override fun onResultSuccessAccount(data: AccountResponse.AccountItem) {
        binding.accountModel = AccountModel(
            acName = data.acName,
            acEmail = data.acEmail,
            acPhone = data.acPhone
        )
    }

    override fun onResultSuccessEngineer(data: EngineerResponse.EngineerItem) {

        binding.engineerModel = EngineerModel(
            enJobTitle = data.enJobTitle,
            enDomicile = data.enDomicile,
            enJobType = data.enJobType,
            enDescription = data.enDescription,
            enProfile = data.enProfile,
            acName = data.acName,
            acId = data.acId,
            enId = data.enId
        )

        /*if (data.enProfile != null) {
            binding.imageUrl = ApiClient.BASE_URL_IMAGE + data.enProfile
        } else {
            binding.imageUrl = ApiClient.BASE_URL_IMAGE_DEFAULT_PROFILE_2
        }*/
    }

    override fun onResultSuccessSkill(list: List<SkillModel>) {
        (binding.rvSkill.adapter as ProfileSkillAdapter).addList(list)
        binding.flSkill.visibility = View.VISIBLE
    }

    override fun onResultSuccessHire(status: Boolean) {
        if (status) {
            binding.btnHire.visibility = View.VISIBLE
        } else {
            binding.btnHire.visibility = View.GONE
        }
    }

    override fun onResultFail(message: String) {
        if (message == "expired") {
            noticeToast("Please sign back in!")
            sharedPref.accountLogout()
        } else {
            binding.flSkill.visibility = View.GONE
            binding.tvDataNotFound.visibility = View.VISIBLE
            binding.dataNotFound = message
        }
    }

    override fun onResultFailHire(message: String) {
        if (message == "expired") {
            noticeToast("Please sign back in!")
            sharedPref.accountLogout()
        } else {
            if (sharedPref.getLevelUser() == 0) {
                binding.btnHire.visibility = View.GONE
            } else {
                binding.btnHire.visibility = View.VISIBLE
            }
        }
    }

    override fun showLoading() {
        binding.shimmerViewContainer.visibility = View.VISIBLE
        binding.progressBar.visibility = View.VISIBLE

        binding.toolbar.visibility = View.GONE
        binding.cvIdentity.visibility = View.GONE
        binding.cvContact.visibility = View.GONE
        binding.cvSkill.visibility = View.GONE
        binding.cvCurriculumVitae.visibility = View.GONE
        binding.flSkill.visibility = View.GONE
        binding.btnHire.visibility = View.GONE
    }

    override fun hideLoading() {
        binding.toolbar.visibility = View.VISIBLE
        binding.cvIdentity.visibility = View.VISIBLE
        binding.cvContact.visibility = View.VISIBLE
        binding.cvSkill.visibility = View.VISIBLE
        binding.cvCurriculumVitae.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE

        binding.shimmerViewContainer.stopShimmerAnimation()
        binding.shimmerViewContainer.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
    }

    override fun onStart() {
        super.onStart()
        binding.shimmerViewContainer.startShimmerAnimation()
        sharedPref.createInDetail(1)

        presenter?.bindToView(this@ProfileEngineerDetailActivity)
        presenter?.callServiceAccount(acId = acId)
        presenter?.callServiceEngineer(acId = acId)
        presenter?.callServiceSkill(enId = enId)
        presenter?.callServiceIsHire(
            cnId = sharedPref.getIdCompany(),
            enId = enId
        )
    }

    override fun onPause() {
        super.onPause()
        binding.shimmerViewContainer.stopShimmerAnimation()
    }

    override fun onStop() {
        super.onStop()
        presenter?.unbind()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter = null
    }

    private fun setToolbarActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Profile Detail"
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initViewPager() {
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        val adapter = ViewPagerAdapter(supportFragmentManager)

        adapter.addFrag(DetailProfilePortfolioFragment(enId!!), "Portfolio")
        adapter.addFrag(DetailProfileExperienceFragment(enId!!), "Experience")
        binding.viewPager.adapter = adapter
    }

    private fun setSkillRecyclerView() {
        binding.rvSkill.layoutManager = FlexboxLayoutManager(this@ProfileEngineerDetailActivity)

        val adapter = ProfileSkillAdapter()
        binding.rvSkill.adapter = adapter
    }
}
