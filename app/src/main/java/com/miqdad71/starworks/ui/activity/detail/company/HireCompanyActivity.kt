package com.miqdad71.starworks.ui.activity.detail.company

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
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
    
    private lateinit var viewModel: HireViewModel
    private var pjId: Int? = 0
    private var enId: Int? = 0
    
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hire_company)
        super.onCreate(savedInstanceState)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        sharedPref = SharedPreference(this)
        enId = intent.getIntExtra("en_id", 0)

        getAllProject()
        setToolbarActionBar()
        setProjectAdapter()

        setViewModel()
//        subscribeLiveData()
        

    }

    private fun setToolbarActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
    private fun setProjectAdapter() {
        val adapter = ProjectSpinnerAdapter(this@HireCompanyActivity)
        binding.spProject.adapter = adapter
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this@HireCompanyActivity).get(HireViewModel::class.java)
        viewModel.setService(createApi(this@HireCompanyActivity))
    }
    
    private inline fun <reified ApiService> createApi(context: Context): ApiService {
        return ApiClient.getApiClient(context).create(ApiService::class.java)
    }

    /*private fun subscribeLiveData() {
        viewModel.isLoadingLiveData.observe(this@HireCompanyActivity, {
            binding.btnProcessHire.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        })

        viewModel.onSuccessLiveData.observe(this@HireCompanyActivity, {
            if (it) {
                setResult(RESULT_OK)
                this@HireCompanyActivity.finish()

                binding.progressBar.visibility = View.GONE
                binding.btnProcessHire.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.btnProcessHire.visibility = View.VISIBLE
            }
        })

        viewModel.onMessageLiveData.observe(this@HireActivity, {
            noticeToast(it)
        })

        viewModel.onFailLiveData.observe(this@HireActivity, {
            noticeToast(it)
        })
    }*/

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
        }
    }

    private fun hireEngineer(enId: Int.Companion, pjId: Int.Companion, hrPrice: Long.Companion, hrMessage: String.Companion) {
        val service = ApiClient.getApiClient(this).create(HireAPI::class.java)
        coroutineScope.launch {
            val res = withContext(Dispatchers.IO) {
                try {
                    service.createHire(
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