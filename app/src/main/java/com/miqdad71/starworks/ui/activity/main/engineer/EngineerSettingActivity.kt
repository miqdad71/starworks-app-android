package com.miqdad71.starworks.ui.activity.main.engineer

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.miqdad71.starworks.R
import com.miqdad71.starworks.data.remote.ApiClient
import com.miqdad71.starworks.databinding.ActivityEngineerEditProfileBinding
import com.miqdad71.starworks.ui.activity.signup.SignUpActivity
import com.miqdad71.starworks.ui.activity.skill.SkillViewModel
import com.miqdad71.starworks.util.SharedPreference

class EngineerSettingActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityEngineerEditProfileBinding

    private lateinit var preference: SharedPreference
    private lateinit var viewModel: EngineerViewModel
    private var enId: Int? = null
    private var acId: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        binding =DataBindingUtil.setContentView(this, R.layout.activity_engineer_edit_profile)
        super.onCreate(savedInstanceState)

        preference = SharedPreference(this)
        enId = intent.getIntExtra("en_id", 0)
        acId = intent.getIntExtra("ac_id", 0)


        binding.etJobTitle.setText(intent.getStringExtra("en_job_title"))
        binding.etJobType.setText(intent.getStringExtra("en_job_type"))
        binding.etDomicile.setText(intent.getStringExtra("en_domicile"))
        binding.etDescription.setText(intent.getStringExtra("en_description"))

        setToolbarActionBar()
        setViewModel()
        subscribeLiveData()
    }
    private fun setToolbarActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Profile"
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this).get(EngineerViewModel::class.java)
        viewModel.setService(createApi(this))
        viewModel.setServiceAccount(createApi(this))
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
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_edit_profile -> {
                val enJobTitle = binding.etJobTitle.text.toString()
                val enJobType = binding.etJobType.text.toString()
                val enDomicile = binding.etDomicile.text.toString()
                val enDescription = binding.etDescription.text.toString()
                val acName = binding.etEditName.text.toString()


                if (enJobTitle.isEmpty()) {
                    binding.etJobTitle.error = SignUpActivity.FIELD_REQUIRED
                    return
                }
                if (enJobType.isEmpty()) {
                    binding.etJobType.error = SignUpActivity.FIELD_REQUIRED
                    return
                }
                if (enDomicile.isEmpty()) {
                    binding.etDomicile.error = SignUpActivity.FIELD_REQUIRED
                    return
                }
                if (enDescription.isEmpty()) {
                    binding.etDescription.error = SignUpActivity.FIELD_REQUIRED
                    return
                }
                if (acName.isEmpty()) {
                    binding.etEditName.error = SignUpActivity.FIELD_REQUIRED
                    return
                }

                viewModel.updateAPI(
                    enId = enId!!,
                    enJobTitle = enJobTitle,
                    enJobType = enJobType,
                    enDomicile = enDomicile,
                    enDescription = enDescription,
                    acId = acId!!,
                    acName = acName
                    )
                finish()
            }
            /*R.id.btn_delete_skill -> {
                viewModel.deleteAPI(
                    skId = skId!!
                )
                finish()
            }*/
        }
    }

}