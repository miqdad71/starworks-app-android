package com.miqdad71.starworks.ui.activity.detail.company

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.miqdad71.starworks.R
import com.miqdad71.starworks.data.model.project.ProjectModel
import com.miqdad71.starworks.data.remote.ApiClient
import com.miqdad71.starworks.databinding.ActivityHireCompanyBinding
import com.miqdad71.starworks.serviceapi.ProjectAPI
import com.miqdad71.starworks.ui.activity.signup.SignUpActivity
import com.miqdad71.starworks.ui.adapter.project.ProjectSpinnerAdapter
import com.miqdad71.starworks.util.SharedPreference
import kotlinx.coroutines.*

class HireCompanyActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityHireCompanyBinding
    
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var sharedPref: SharedPreference
    private lateinit var viewModel: HireViewModel
    private var pjId: Int? = 0
    private var enId: Int? = null

    companion object {
        const val FIELD_REQUIRED = "Fields cannot be empty"
        const val FIELD_DIGITS_ONLY = "Can only contain numerics"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hire_company)
        super.onCreate(savedInstanceState)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        sharedPref = SharedPreference(this)
        enId = intent.getIntExtra("en_id", 0)
        pjId = intent.getIntExtra("pj_id", 0)

        setToolbarActionBar()
        setProjectAdapter()
        getAllProject()

        setViewModel()
        subscribeLiveData()
        

    }

    private fun setToolbarActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Hire"
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
    private fun setProjectAdapter() {
        val adapter = ProjectSpinnerAdapter(this)
        binding.spProject.adapter = adapter
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this).get(HireViewModel::class.java)
        viewModel.setService(createApi(this))
    }
    private fun subscribeLiveData() {
        viewModel.onSuccessLiveData.observe(this) {
            if (it) {
                setResult(RESULT_OK)
                this.finish()
            }
        }
    }
    private inline fun <reified ApiService> createApi(context: Context): ApiService {
        return ApiClient.getApiClient(context).create(ApiService::class.java)
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
                val price = binding.etPrice.text.toString()
                val desc = binding.etDescription.text.toString()

                if (price.isEmpty()) {
                    binding.etPrice.error = FIELD_DIGITS_ONLY
                    return
                }

                if (desc.isEmpty()) {
                    binding.etDescription.error = FIELD_REQUIRED
                    return
                }
                if(enId != 0){
                    viewModel.createAPI(
                        enId = enId!!,
                        pjId = sharedPref.getIdCompany(),
                        hrPrice = binding.etPrice.text.toString().toLong(),
                        hrMessage = binding.etDescription.text.toString()
                    )
                }

            }
        }
    }
}