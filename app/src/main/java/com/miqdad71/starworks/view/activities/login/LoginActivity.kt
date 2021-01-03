package com.miqdad71.starworks.view.activities.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.miqdad71.starworks.R
import com.miqdad71.starworks.api.AccountAPI
import com.miqdad71.starworks.databinding.ActivityLoginBinding
import com.miqdad71.starworks.remote.ApiClient
import com.miqdad71.starworks.util.SharedPreference
import com.miqdad71.starworks.view.activities.forgetpassword.ForgetPasswordVerifyActivity
import com.miqdad71.starworks.view.activities.main.company.CompanyMainActivity
import com.miqdad71.starworks.view.activities.main.engineer.EngineerMainActivity
import com.miqdad71.starworks.view.activities.signup.SignUpActivity
import com.miqdad71.starworks.view.dialog.Dialog
import com.miqdad71.starworks.view.model.company.CompanyModel
import com.miqdad71.starworks.view.model.engineer.EngineerModel
import com.miqdad71.starworks.view.model.account.LoginResponse
import kotlinx.coroutines.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var preference: SharedPreference
    private lateinit var engineerModel: EngineerModel
    private lateinit var companyModel: CompanyModel
    private lateinit var dialog: Dialog
    private lateinit var coroutineScope: CoroutineScope


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        engineerModel = EngineerModel()
        companyModel = CompanyModel()
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )
        dialog = Dialog()

        binding.tvForgetPassword.setOnClickListener(this)
        binding.login.setOnClickListener(this)
        binding.tvSignUp.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val acLevel = intent.getIntExtra("level", 0)

        when (v?.id) {
            R.id.tv_forget_password -> {
                val intent = Intent(this, ForgetPasswordVerifyActivity::class.java)
                startActivity(intent)
            }
            R.id.tv_sign_up -> {
                val intent = Intent(this, SignUpActivity::class.java)
                intent.putExtra("level", acLevel)
                startActivity(intent)
            }
            R.id.login -> {
                login(v)
            }
        }
    }

    private fun login(view: View) {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val acLevel = intent.getIntExtra("level", 0)

        preference = SharedPreference(view.context)
        engineerModel = preference.getEngineerPreference(engineerModel)
        companyModel = preference.getCompanyPreference(companyModel)

        if (email.isEmpty()) {
            binding.etEmail.error = SignUpActivity.FIELD_REQUIRED
            return
        }

        if (password.isEmpty()) {
            binding.etPassword.error = SignUpActivity.FIELD_REQUIRED
            return
        }
        loginAccount()
    }

    fun loginAccount() {
        val api = ApiClient.getApiClient(this).create(AccountAPI::class.java)
        coroutineScope.launch {
            val res = withContext(Dispatchers.IO) {
                try {
                    api.login(
                        binding.etEmail.text.toString(),
                        binding.etPassword.text.toString()
                    )
                } catch (t: Exception) {
                    Log.e("Error", t.localizedMessage)
                }
            }
            if (res is LoginResponse) {
                val data = res.data
                if (data.ac_level == 0) {
                    preference.setAccount(data.ac_name, data.ac_id, data.ac_level, data.ac_email)
                    val sendIntent = Intent(this@LoginActivity, EngineerMainActivity::class.java)
                    startActivity(sendIntent)
                    this@LoginActivity.finish()
                } else {
                    preference.setAccount(data.ac_name, data.ac_id, data.ac_level, data.ac_email)
                    val sendIntent = Intent(this@LoginActivity, CompanyMainActivity::class.java)
                    startActivity(sendIntent)
                    this@LoginActivity.finish()
                }
            }

        }
    }
}