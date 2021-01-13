package com.miqdad71.starworks.ui.activity.detail.company

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.miqdad71.starworks.R
import com.miqdad71.starworks.serviceapi.HireAPI
import com.miqdad71.starworks.serviceapi.ProjectAPI
import com.miqdad71.starworks.data.model.hire.HireResponse
import com.miqdad71.starworks.data.model.project.ProjectModel
import com.miqdad71.starworks.data.remote.ApiClient
import com.miqdad71.starworks.databinding.ActivityHireCompanyBinding
import com.miqdad71.starworks.ui.activity.signup.SignUpActivity
import com.miqdad71.starworks.ui.adapter.project.ProjectSpinnerAdapter
import com.miqdad71.starworks.util.SharedPreference
import kotlinx.coroutines.*
import java.util.HashMap

class HireCompanyActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityHireCompanyBinding
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var sharedPref: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hire_company)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        sharedPref = SharedPreference(this)
        
        getAllProject()
        setToolbarActionBar()
        val adapter = ProjectSpinnerAdapter(this@HireCompanyActivity)

        binding.spProject.adapter = adapter

        Log.e("GetID", "${sharedPref.getIdCompany()}")
    }

    private fun setToolbarActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun getAllProject() {
        val service = ApiClient.getApiClient(this).create(ProjectAPI::class.java)

        coroutineScope.launch {
            try {
                val resultData = service.getAllProject(sharedPref.getIdCompany())
                val dataFromResult = resultData.data
                val list = dataFromResult.map {
                    ProjectModel(
                        pjId = it.pjId,
                        cnId = it.cnId,
                        pjProjectName = it.pjProjectName,
                        pjDescription = it.pjDescription,
                        pjDeadline = it.pjDeadline,
                        pjImage = it.pjImage
                    )
                }
                (binding.spProject.adapter as ProjectSpinnerAdapter).addList(list)

            }catch (e: Throwable) {

            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_process_hire -> {
                    hire(v)
            }
            R.id.ln_back -> {
                this@HireCompanyActivity.finish()
            }
        }
    }

    private fun hire(view: View) {
        val price = binding.etPrice.text.toString()
        val desc = binding.etDescription.text.toString()

        if (price.isEmpty()) {
            binding.etPrice.error = SignUpActivity.FIELD_REQUIRED
            return
        }

        if (desc.isEmpty()) {
            binding.etDescription.error = SignUpActivity.FIELD_REQUIRED
            return
        }
        hireEngineer(enId = Int, pjId = Int, hrPrice = Long, hrMessage = String)
    }

    fun hireEngineer(enId: Int.Companion, pjId: Int.Companion, hrPrice: Long.Companion, hrMessage: String.Companion) {
        val service = ApiClient.getApiClient(this).create(HireAPI::class.java)
        coroutineScope.launch {
            val res = withContext(Dispatchers.IO) {
                try {
                    service.hire(
                        enId = enId,
                        pjId = pjId,
                        hrPrice = hrPrice,
                        hrMessage = hrMessage
                        )

                } catch (t: Exception) {
                    Log.e("Error", t.localizedMessage)
                }
            }

            if (res is HireResponse) {
                    finish()
            }

        }
    }
}