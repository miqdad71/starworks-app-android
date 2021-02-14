package com.miqdad71.starworks.ui.activity.detail.company

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.miqdad71.starworks.R
import com.miqdad71.starworks.data.model.hire.HireModel
import com.miqdad71.starworks.data.model.hire.HireResponse
import com.miqdad71.starworks.data.remote.ApiClient
import com.miqdad71.starworks.data.remote.ApiClient.Companion.BASE_URL_IMAGE
import com.miqdad71.starworks.databinding.ActivityProjectDetailBinding
import com.miqdad71.starworks.serviceapi.HireAPI
import com.miqdad71.starworks.ui.base.BaseActivityCoroutine
import kotlinx.android.synthetic.main.activity_project_detail.*
import kotlinx.coroutines.*

class ProjectDetailActivity : BaseActivityCoroutine<ActivityProjectDetailBinding>(), View.OnClickListener {

    private var hrId: Int? = null
    private var hrStatus: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_project_detail
        super.onCreate(savedInstanceState)

        hrId = intent.getIntExtra("hr_id", 0)
        hrStatus = intent.getStringExtra("hr_status")

        if (hrStatus != "wait") {
            binding.btnApprove.visibility = View.GONE
            binding.btnCancel.visibility = View.GONE
        }

        setToolbarActionBar()
        setIntentData()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_approve -> {
                approveHire()
            }
            R.id.btn_cancel -> {
                rejectHire()
            }
        }
    }

    private fun setToolbarActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detail Hiring Project"
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setIntentData() {
        binding.hire = HireModel(
            hrId = hrId,
            enId = intent.getIntExtra("en_id", 0),
            pjId = intent.getIntExtra("pj_id", 0),
            hrPrice = intent.getLongExtra("hr_price", 0),
            hrMessage = intent.getStringExtra("hr_message"),
            hrStatus = hrStatus,
            hrDateConfirm = intent.getStringExtra("hr_date_confirm"),
            pjProjectName = intent.getStringExtra("pj_project_name"),
            pjDescription = intent.getStringExtra("pj_description"),
            pjDeadline = intent.getStringExtra("pj_deadline")!!.split('T')[0],
            pjImage = intent.getStringExtra("pj_image"),
            cnCompany = intent.getStringExtra("cn_company"),
            cnField = intent.getStringExtra("cn_field"),
            cnCity = intent.getStringExtra("cn_city")
            )
        Glide.with(this).load(BASE_URL_IMAGE + intent.getStringExtra("cn_profile")).placeholder(R.drawable.ic_backround_user).into(binding.ivImageProfile)
    }

    private fun approveHire() {
        val service = createApi<HireAPI>(this@ProjectDetailActivity)

        coroutineScope.launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service.updateHireStatus(
                        hrId = hrId!!,
                        hrStatus = "approve"
                    )
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is HireResponse) {
                noticeToast("Hire is approved")
                setResult(RESULT_OK)
                this@ProjectDetailActivity.finish()
            }
        }
    }

    private fun rejectHire() {
        val service = createApi<HireAPI>(this@ProjectDetailActivity)

        coroutineScope.launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service.updateHireStatus(
                        hrId = hrId!!,
                        hrStatus = "reject"
                    )
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is HireResponse) {
                noticeToast("Hire is rejected")
                setResult(RESULT_OK)
                this@ProjectDetailActivity.finish()
            }
        }
    }
}