package com.miqdad71.starworks.ui.activity.skill

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.miqdad71.starworks.R
import com.miqdad71.starworks.data.remote.ApiClient
import com.miqdad71.starworks.databinding.ActivitySkillBinding
import com.miqdad71.starworks.util.SharedPreference

class SkillActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySkillBinding

    private lateinit var sharedPref: SharedPreference
    private lateinit var viewModel: SkillViewModel
    companion object {
        const val FIELD_REQUIRED = "Fields cannot be empty"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_skill)
        super.onCreate(savedInstanceState)

        sharedPref = SharedPreference(this)

        setToolbarActionBar()
        setViewModel()
        subscribeLiveData()

    }

    private fun setToolbarActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Skill"
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
    private fun setViewModel() {
        viewModel = ViewModelProvider(this).get(SkillViewModel::class.java)
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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_add_skill -> {
                val skSkillName = binding.etSkill.text.toString()
                if (skSkillName.isEmpty()) {
                    binding.etSkill.error = FIELD_REQUIRED
                    return
                }
                viewModel.createAPI(
                    enId = sharedPref.getIdEngineer(),
                    skSkillName = skSkillName
                )
            }
            R.id.ln_back -> {
                this@SkillActivity.finish()
            }
        }
    }
}