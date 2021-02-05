package com.miqdad71.starworks.ui.activity.main.engineer

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.miqdad71.starworks.R
import com.miqdad71.starworks.data.remote.ApiClient
import com.miqdad71.starworks.databinding.ActivityEngineerEditProfileBinding
import com.miqdad71.starworks.ui.activity.signup.SignUpActivity
import com.miqdad71.starworks.ui.activity.skill.SkillViewModel
import com.miqdad71.starworks.util.SharedPreference
import java.util.HashMap

class EngineerSettingActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityEngineerEditProfileBinding

    private lateinit var sharedPref: SharedPreference
    private lateinit var userDetail: HashMap<String, String>
    private lateinit var viewModel: EngineerViewModel

    private var enId: Int? = null
    private var acId: Int? = 0

    companion object {
        const val FIELD_REQUIRED = "Fields cannot be empty"
        const val FIELD_DIGITS_ONLY = "Can only contain numerics"
        const val FIELD_IS_NOT_VALID = "Invalid email"
        const val FIELD_MUST_MATCH = "Password must be the same"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        binding =DataBindingUtil.setContentView(this, R.layout.activity_engineer_edit_profile)
        super.onCreate(savedInstanceState)

        sharedPref = SharedPreference(this)
        userDetail = sharedPref.getAccountUser()
        acId = intent.getIntExtra("ac_id", 0)
        enId = intent.getIntExtra("en_id", 0)

        setToolbarActionBar()
        setDataFromIntent()

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

    private fun setDataFromIntent() {
        if (enId != 0) {
            binding.etEditName.setText(intent.getStringExtra("ac_name"))
            binding.etJobTitle.setText(intent.getStringExtra("en_job_title"))
            binding.etJobType.setText(intent.getStringExtra("en_job_type"))
            binding.etDomicile.setText(intent.getStringExtra("en_domicile"))
            binding.etDescription.setText(intent.getStringExtra("en_description"))
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
                    binding.etJobTitle.error = FIELD_REQUIRED
                    return
                }
                if (enJobType.isEmpty()) {
                    binding.etJobType.error = FIELD_REQUIRED
                    return
                }
                if (enDomicile.isEmpty()) {
                    binding.etDomicile.error = FIELD_REQUIRED
                    return
                }
                if (enDescription.isEmpty()) {
                    binding.etDescription.error = FIELD_REQUIRED
                    return
                }
                if (acName.isEmpty()) {
                    binding.etEditName.error = FIELD_REQUIRED
                    return
                }

                viewModel.updateAPI(
                    enId = sharedPref.getIdEngineer(),
                    acId = sharedPref.getIdAccount(),
                    enJobTitle = enJobTitle,
                    enJobType = enJobType,
                    enDomicile = enDomicile,
                    enDescription = enDescription,
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