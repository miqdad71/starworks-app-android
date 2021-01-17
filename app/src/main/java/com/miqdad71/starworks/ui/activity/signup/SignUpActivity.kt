package com.miqdad71.starworks.ui.activity.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.miqdad71.starworks.R
import com.miqdad71.starworks.serviceapi.AccountAPI
import com.miqdad71.starworks.data.model.account.SignUpResponse
import com.miqdad71.starworks.databinding.ActivitySignUpBinding
import com.miqdad71.starworks.util.SharedPreference
import com.miqdad71.starworks.ui.activity.login.LoginActivity
import com.miqdad71.starworks.data.remote.ApiClient
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
    private lateinit var preference: SharedPreference
    private lateinit var coroutineScope: CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

        supportActionBar?.hide()
        @Suppress("DEPRECATION")
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
                Toast.makeText(this, "Register Success!", Toast.LENGTH_SHORT).show()
            }
            R.id.tv_login -> {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }

    private fun signUp(view: View) {
        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString()
        val company = binding.etCompany.text.toString()
        val position = binding.etPosition.text.toString()
        val phone = binding.etPhoneNumber.text.toString()
        val password = binding.etPassword.text.toString()
        val passwordConfirmation = binding.etPasswordConfirm.text.toString()

        preference = SharedPreference(view.context)

        if (intent.getIntExtra("level", 0) == 0) {

            if (name.isEmpty()) {
            binding.etName.error = FIELD_REQUIRED
            return
            }
            if (email.isEmpty()) {
            binding.etEmail.error = FIELD_IS_NOT_VALID
            return
            }
            if (phone.isEmpty()) {
                binding.etPhoneNumber.error = FIELD_DIGITS_ONLY
                return
            }
            if (password.isEmpty()) {
                binding.etPassword.error = FIELD_REQUIRED
                return
            }
            if (password != passwordConfirmation) {
                binding.etPasswordConfirm.error = FIELD_MUST_MATCH
                return
            }
        } else {
            if (name.isEmpty()) {
                binding.etName.error = FIELD_REQUIRED
                return
            }
            if (email.isEmpty()) {
                binding.etEmail.error = FIELD_IS_NOT_VALID
                return
            }
            if (company.isEmpty()) {
                binding.etCompany.error = FIELD_IS_NOT_VALID
                return
            }
            if (position.isEmpty()) {
                binding.etPosition.error = FIELD_IS_NOT_VALID
                return
            }
            if (phone.isEmpty()) {
                binding.etPhoneNumber.error = FIELD_DIGITS_ONLY
                return
            }
            if (password.isEmpty()) {
                binding.etPassword.error = FIELD_REQUIRED
                return
            }
            if (password != passwordConfirmation) {
                binding.etPasswordConfirm.error = FIELD_MUST_MATCH
                return
            }
        }
        signUpAccount()
    }

    private fun signUpAccount() {
        val api = ApiClient.getApiClient(this).create(AccountAPI::class.java)
        coroutineScope.launch {
            val res = withContext(Dispatchers.IO) {
                try {
                    if (intent.getIntExtra("level", 0) == 0) {
                        api.signUpEngineerAccount(
                            acName = binding.etName.text.toString(),
                            acEmail = binding.etEmail.text.toString(),
                            acPhone = binding.etPhoneNumber.text.toString(),
                            acPassword = binding.etPassword.text.toString(),
                            acLevel = 0
                        )
                    } else {
                        api.signUpCompanyAccount(
                            acName = binding.etName.text.toString(),
                            acEmail = binding.etEmail.text.toString(),
                            acPhone = binding.etPhoneNumber.text.toString(),
                            acPassword = binding.etPassword.text.toString(),
                            acLevel = 1,
                            cnCompany = binding.etCompany.text.toString(),
                            cnPosition = binding.etPosition.text.toString()
                        )
                    }
                } catch (e: HttpException) {
                    runOnUiThread {
                        if (e.code() == 400) {
                            Toast.makeText(this@SignUpActivity, "Email has registered!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@SignUpActivity, "Fail to registration! Please try again later!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            if (res is SignUpResponse) {
                if (res.success) {
                    Toast.makeText(this@SignUpActivity, res.message, Toast.LENGTH_SHORT).show()
                    this@SignUpActivity.finish()
                } else {
                    Toast.makeText(this@SignUpActivity, res.message, Toast.LENGTH_SHORT).show()
                }
            }
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}