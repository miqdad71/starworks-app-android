package com.miqdad71.starworks.ui.activity.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.miqdad71.starworks.R
import com.miqdad71.starworks.serviceapi.AccountAPI
import com.miqdad71.starworks.databinding.ActivityLoginBinding
import com.miqdad71.starworks.data.remote.ApiClient
import com.miqdad71.starworks.util.SharedPreference
import com.miqdad71.starworks.ui.activity.forgetpassword.ForgetPasswordVerifyActivity
import com.miqdad71.starworks.ui.activity.main.company.CompanyMainActivity
import com.miqdad71.starworks.ui.activity.main.engineer.EngineerMainActivity
import com.miqdad71.starworks.ui.activity.signup.SignUpActivity
import com.miqdad71.starworks.data.model.account.LoginResponse
import kotlinx.coroutines.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var preference: SharedPreference
    private lateinit var coroutineScope: CoroutineScope


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )

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
        preference = SharedPreference(view.context)

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
                Log.d("loginData", data.toString())
                val level = data.acLevel

                preference.setToken(data.token)
                when (level) {
                    0 -> {
                        preference.setAccountEngineer(
                            acName = data.acName,
                            acId = data.acId,
                            enId = data.enId,
                            acLevel = data.acLevel,
                            acEmail = data.acEmail
                        )
                        val sendIntent =
                            Intent(this@LoginActivity, EngineerMainActivity::class.java)
                        startActivity(sendIntent)
                        this@LoginActivity.finish()
                    }
                    1 -> {
                        preference.setAccountCompany(
                            acName = data.acName,
                            acId = data.acId,
                            cnId = data.cnId,
                            acLevel = data.acLevel,
                            acEmail = data.acEmail
                        )
                        val sendIntent = Intent(this@LoginActivity, CompanyMainActivity::class.java)
                        startActivity(sendIntent)
                        this@LoginActivity.finish()
                    }
                }
            }
        }
    }
}