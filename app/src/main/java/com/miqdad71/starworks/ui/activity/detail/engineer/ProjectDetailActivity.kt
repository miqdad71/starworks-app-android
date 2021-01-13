package com.miqdad71.starworks.ui.activity.detail.engineer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.miqdad71.starworks.R
import com.miqdad71.starworks.data.model.engineer.EngineerModel
import com.miqdad71.starworks.data.model.hire.HireModel
import com.miqdad71.starworks.data.model.hire.HireResponse
import com.miqdad71.starworks.data.remote.ApiClient
import com.miqdad71.starworks.databinding.ActivityProjectDetailBinding
import com.miqdad71.starworks.serviceapi.HireAPI
import com.miqdad71.starworks.ui.activity.signup.SignUpActivity
import com.miqdad71.starworks.util.SharedPreference
import kotlinx.coroutines.*

class ProjectDetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityProjectDetailBinding
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var sharedPref: SharedPreference
    private var hrId: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_project_detail)
        
        hrId = intent.getIntExtra("hr_id", 0)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        sharedPref = SharedPreference(this)
        
        
        setHire()
        setToolbarActionBar()

    }
    
    private fun setToolbarActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Project Detail"
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
    
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_approve -> {
                approve()
            }
            R.id.btn_reject -> {
                reject()
            }
        }
    }
    
    private fun approve() {
        approveProject(hrId = intent.getIntExtra("hr_id", 0), hrStatus = "approve")
    }

    private fun reject() {
        approveProject(hrId = intent.getIntExtra("hr_id", 0), hrStatus = "reject")
    }

    fun approveProject(hrId: Int, hrStatus: String) {
        val service = ApiClient.getApiClient(this).create(HireAPI::class.java)
        coroutineScope.launch {
            val res = withContext(Dispatchers.IO) {
                try {
                    service.updateHireStatus(
                        hrId = hrId,
                        hrStatus = hrStatus

                    )

                } catch (t: Exception) {
                    Log.e("Error", t.localizedMessage)
                }
            }

            if (res is HireResponse) {
                Toast.makeText(this@ProjectDetailActivity, "Success Hire", Toast.LENGTH_SHORT).show()
                finish()
            }

        }
    }


    private fun setHire() {
        binding.hire = HireModel(
            hrId = hrId!!,
            enId = intent.getIntExtra("en_id", 0),
            pjId = intent.getIntExtra("pj_id", 0),
            pjProjectName = intent.getStringExtra("pj_project_name"),
            pjDescription = intent.getStringExtra("pj_description"),
            pjDeadline = intent.getStringExtra("pj_deadline"),
            hrPrice = intent.getLongExtra("hr_price", 0),
            hrMessage = intent.getStringExtra("hr_message"),
            hrStatus = intent.getStringExtra("hr_status"),
            cnCompany = intent.getStringExtra("cn_company"),
            cnField = intent.getStringExtra("cn_field"),
            cnCity = intent.getStringExtra("cn_city"),
            cnProfile = intent.getStringExtra("cn_profile")
        )
        binding.imageUrl = ApiClient.BASE_URL_IMAGE + intent.getStringExtra("en_profile")

    }

}