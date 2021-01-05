package com.miqdad71.starworks.ui.activities.signup

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.miqdad71.starworks.R
import com.miqdad71.starworks.api.AccountAPI
import com.miqdad71.starworks.data.model.account.LoginResponse
import com.miqdad71.starworks.databinding.ActivitySignUpBinding
import com.miqdad71.starworks.util.SharedPreference
import com.miqdad71.starworks.ui.activities.main.engineer.EngineerMainActivity
import com.miqdad71.starworks.ui.activities.login.LoginActivity
import com.miqdad71.starworks.ui.activities.dialog.Dialog
import com.miqdad71.starworks.data.model.engineer.EngineerModel
import com.miqdad71.starworks.data.remote.ApiClient
import com.miqdad71.starworks.ui.activities.forgetpassword.ForgetPasswordVerifyActivity
import com.miqdad71.starworks.ui.activities.main.company.CompanyMainActivity
import kotlinx.coroutines.*
import retrofit2.HttpException

class SignUpActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val FIELD_REQUIRED = "Fields cannot be empty"
        const val FIELD_DIGITS_ONLY = "Can only contain numerics"
        const val FIELD_IS_NOT_VALID = "Invalid email"
        const val FIELD_MUST_MATCH = "Password must be the same"
    }

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var dialog: Dialog
    private lateinit var preference: SharedPreference
    private lateinit var coroutineScope: CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )

        if (intent.getIntExtra("level", 0) == 1) {
            binding.clCompany.visibility = View.VISIBLE
        } else {
            binding.clCompany.visibility = View.GONE
        }

        binding.btnSignUp.setOnClickListener(this)
        binding.tvLogin.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_sign_up -> {
                signUp(v)
            }
            R.id.tv_login -> {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }

    private fun signUp(view: View) {
        val nama = binding.etName.text.toString()
        val email = binding.etEmail.text.toString()
        val phone = binding.etPhoneNumber.text.toString()
        val password = binding.etPassword.text.toString()
        val passwordconfirmation = binding.etPasswordConfirm.text.toString()

        preference = SharedPreference(view.context)

        if (password.isEmpty()) {
            binding.etName.error = FIELD_REQUIRED
            return
        }
        if (email.isEmpty()) {
            binding.etEmail.error = FIELD_REQUIRED
            return
        }
        if (password.isEmpty()) {
            binding.etPhoneNumber.error = FIELD_REQUIRED
            return
        }
        if (password.isEmpty()) {
            binding.etPassword.error = FIELD_REQUIRED
            return
        }
        if (password.isEmpty()) {
            binding.etPasswordConfirm.error = FIELD_REQUIRED
            return
        }
        signUpAccount()
    }
    fun signUpAccount() {
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
                preference.setAccount(data.acName, data.acId, data.acLevel, data.acEmail)
                preference.setToken(data.token)
                when(level) {
                    0 -> {
                        val sendIntent = Intent(this@SignUpActivity, EngineerMainActivity::class.java)
                        startActivity(sendIntent)
                        this@SignUpActivity.finish()
                    }
                    1 -> {
                        val sendIntent = Intent(this@SignUpActivity, CompanyMainActivity::class.java)
                        startActivity(sendIntent)
                        this@SignUpActivity.finish()
                    }
                }
            }
        }
    }
}