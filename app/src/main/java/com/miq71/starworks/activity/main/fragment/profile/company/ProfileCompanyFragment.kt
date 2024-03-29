package com.miq71.starworks.activity.main.fragment.profile.company

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.miq71.starworks.R
import com.miq71.starworks.activity.image_profile.company.ImageProfileCompanyActivity
import com.miq71.starworks.activity.settings.SettingsActivity
import com.miq71.starworks.base.BaseFragmentCoroutine
import com.miq71.starworks.databinding.FragmentProfileCompanyBinding
import com.miq71.starworks.model.account.AccountModel
import com.miq71.starworks.model.account.AccountResponse
import com.miq71.starworks.model.company.CompanyModel
import com.miq71.starworks.model.company.CompanyResponse
import com.miq71.starworks.remote.ApiClient
import com.miq71.starworks.remote.ApiClient.Companion.BASE_URL_IMAGE
import kotlinx.android.synthetic.main.fragment_profile_company.view.*
import kotlinx.android.synthetic.main.fragment_profile_engineer.*
import kotlinx.android.synthetic.main.fragment_profile_engineer.view.*

class ProfileCompanyFragment : BaseFragmentCoroutine<FragmentProfileCompanyBinding>(), ProfileCompanyContract.View, View.OnClickListener {
    private var presenter: ProfileCompanyPresenter? = null

    companion object {
        const val INTENT_EDIT_IMAGE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_profile_company
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = ProfileCompanyPresenter(
            serviceAccount = createApi(activity),
            serviceCompany = createApi(activity)
        )

        setContentViewCompany()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_edit_company -> {
                startActivity(Intent(activity, SettingsActivity::class.java))
            }
            R.id.btn_logout -> {
                logoutConf(activity)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == INTENT_EDIT_IMAGE && resultCode == Activity.RESULT_OK) {
            presenter?.callServiceAccount(sharedPref.getIdAccount())
        }
    }

    override fun onResultSuccessAccount(data: AccountResponse.AccountItem) {
        bind.accountModel = AccountModel(
            acName = data.acName,
            acEmail = data.acEmail,
            acPhone = data.acPhone
        )
    }

    override fun onResultSuccessCompany(data: CompanyResponse.CompanyItem) {
        bind.tvDescription.setShowingLine(2)
        bind.tvDescription.addShowMoreText("read more")
        bind.tvDescription.addShowLessText("less")
        bind.tvDescription.setShowMoreColor(Color.BLUE)
        bind.tvDescription.setShowLessTextColor(Color.BLUE)

        bind.companyModel = CompanyModel(
            cn_company = data.cnCompany,
            cn_position = data.cnPosition,
            cn_field = data.cnField,
            cn_city = data.cnCity,
            cn_description = data.cnDescription,
            cn_instagram = data.cnInstagram,
            cn_linkedin = data.cnLinkedin
        )

        if (data.cnProfile != null) {
            bind.imageUrl = BASE_URL_IMAGE + data.cnProfile
        } else {
            bind.imageUrl = ApiClient.BASE_URL_IMAGE_DEFAULT_PROFILE_2
        }

        bind.ivImageProfile.setOnClickListener {
            val intent = Intent(activity, ImageProfileCompanyActivity::class.java)
            intent.putExtra("cn_id", data.cnId)
            intent.putExtra("cn_profile", data.cnProfile)
            startActivityForResult(intent, INTENT_EDIT_IMAGE)
        }
    }

    override fun onResultFail(message: String) {
        noticeToast(message)
    }

    override fun showLoading() {
        bind.shimmerViewContainer.visibility = View.VISIBLE

        bind.cvIdentity.visibility = View.GONE
        bind.cvContact.visibility = View.GONE
    }

    override fun hideLoading() {
        bind.cvIdentity.visibility = View.VISIBLE
        bind.cvContact.visibility = View.VISIBLE

        bind.shimmerViewContainer.stopShimmerAnimation()
        bind.shimmerViewContainer.visibility = View.GONE
    }

    override fun onStart() {
        super.onStart()
        bind.shimmerViewContainer.startShimmerAnimation()
        sharedPref.createInDetail(0)

        presenter?.bindToView(this@ProfileCompanyFragment)
        presenter?.callServiceAccount(sharedPref.getIdAccount())
        presenter?.callServiceCompany(sharedPref.getIdAccount())
    }

    override fun onPause() {
        super.onPause()
        bind.shimmerViewContainer.stopShimmerAnimation()
    }

    override fun onStop() {
        super.onStop()
        presenter?.unbind()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter = null
    }

    private fun setContentViewCompany() {
        bind.btnEditCompany.setOnClickListener(this@ProfileCompanyFragment)
        bind.btnLogout.setOnClickListener(this@ProfileCompanyFragment)
        bind.ivImageProfile.setOnClickListener(this@ProfileCompanyFragment)
    }
}