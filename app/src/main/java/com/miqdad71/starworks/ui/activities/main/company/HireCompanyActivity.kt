package com.miqdad71.starworks.ui.activities.main.company

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.miqdad71.starworks.R
import com.miqdad71.starworks.api.AccountAPI
import com.miqdad71.starworks.api.HireAPI
import com.miqdad71.starworks.data.model.account.LoginResponse
import com.miqdad71.starworks.data.model.hire.HireResponse
import com.miqdad71.starworks.data.remote.ApiClient
import com.miqdad71.starworks.databinding.ActivityHireCompanyBinding
import com.miqdad71.starworks.ui.activities.main.engineer.EngineerMainActivity
import com.miqdad71.starworks.ui.activities.signup.SignUpActivity
import com.miqdad71.starworks.util.SharedPreference
import kotlinx.coroutines.*

class HireCompanyActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityHireCompanyBinding
    private lateinit var coroutineScope: CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hire_company)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        setToolbarActionBar()

    }

    private fun setToolbarActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
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
        val email = binding.etPrice.text.toString()
        val password = binding.etDescription.text.toString()

        if (email.isEmpty()) {
            binding.etPrice.error = SignUpActivity.FIELD_REQUIRED
            return
        }

        if (password.isEmpty()) {
            binding.etDescription.error = SignUpActivity.FIELD_REQUIRED
            return
        }
        hireEngineer()
    }

    fun hireEngineer() {
        val api = ApiClient.getApiClient(this).create(HireAPI::class.java)
        coroutineScope.launch {
            val res = withContext(Dispatchers.IO) {
                try {
                    api.hire(
                        enId = 1,
                        pjId = 2,
                        hrPrice = binding.etPrice.text.toString(),
                        hrMessage = binding.etDescription.text.toString()
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