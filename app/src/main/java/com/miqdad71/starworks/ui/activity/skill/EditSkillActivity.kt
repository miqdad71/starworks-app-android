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
import com.miqdad71.starworks.databinding.ActivityEditSkillBinding
import com.miqdad71.starworks.util.SharedPreference

class EditSkillActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityEditSkillBinding

    private lateinit var sharedPref: SharedPreference
    private lateinit var viewModel: SkillViewModel
    private var skId: Int? = 0
    companion object {
        const val FIELD_REQUIRED = "Fields cannot be empty"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_skill)
        super.onCreate(savedInstanceState)

        sharedPref = SharedPreference(this)
        skId = intent.getIntExtra("sk_id", 0)

        binding.etSkill.setText(intent.getStringExtra("sk_skill_name"))

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
            R.id.btn_edit_skill -> {
                val skSkillName = binding.etSkill.text.toString()
                if (skSkillName.isEmpty()) {
                    binding.etSkill.error = FIELD_REQUIRED
                    return
                }

                viewModel.updateAPI(
                    skId = skId!!,
                    skSkillName = skSkillName
                )
            }
            R.id.btn_delete_skill -> {
                viewModel.deleteAPI(
                    skId = skId!!
                )
            }
        }
    }
}