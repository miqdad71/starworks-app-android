package com.miqdad71.starworks.ui.activity.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.ActivityLoginBinding
import com.miqdad71.starworks.ui.activity.OnboardActivity
import com.miqdad71.starworks.ui.activity.forgetpassword.ForgetPasswordVerifyActivity
import com.miqdad71.starworks.ui.activity.main.company.CompanyMainActivity
import com.miqdad71.starworks.ui.activity.main.engineer.EngineerMainActivity
import com.miqdad71.starworks.ui.activity.signup.SignUpActivity
import com.miqdad71.starworks.ui.base.BaseActivityCoroutine
import com.miqdad71.starworks.util.form_validate.ValidateAccount.Companion.valEmail
import com.miqdad71.starworks.util.form_validate.ValidateAccount.Companion.valPassword
import kotlinx.coroutines.*

open class LoginActivity : BaseActivityCoroutine<ActivityLoginBinding>(), View.OnClickListener {
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_login
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        initTextWatcher()
        setViewModel()
        subscribeLiveData()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_forget_password -> {
                intents<ForgetPasswordVerifyActivity>(this@LoginActivity)
            }
            R.id.tv_sign_up -> {
                selectSignUpAs()
            }
            R.id.btn_login -> {
                when {
                    !valEmail(binding.inputLayoutEmail, binding.etEmail) -> {
                    }
                    !valPassword(binding.inputLayoutPassword, binding.etPassword) -> {
                    }
                    else -> {
                        viewModel.createAPI(
                            email = binding.etEmail.text.toString(),
                            password = binding.etPassword.text.toString()
                        )
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        intents<OnboardActivity>(this@LoginActivity)
        this@LoginActivity.finish()
    }

    private fun initTextWatcher() {
        binding.etEmail.addTextChangedListener(MyTextWatcher(binding.etEmail))
        binding.etPassword.addTextChangedListener(MyTextWatcher(binding.etPassword))
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this@LoginActivity).get(LoginViewModel::class.java)
        viewModel.setService(createApi(this@LoginActivity))
        viewModel.setSharedPref(sharedPref)
    }

    private fun subscribeLiveData() {
        viewModel.isLoadingLiveData.observe(this@LoginActivity) {
            binding.btnLogin.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        }

        viewModel.onSuccessLiveData.observe(this@LoginActivity) {
            if (it) {
                binding.progressBar.visibility = View.GONE
                binding.btnLogin.visibility = View.VISIBLE

                if (sharedPref.getLevelUser() == 0) {
                    intents<EngineerMainActivity>(this@LoginActivity)
                } else {
                    intents<CompanyMainActivity>(this@LoginActivity)
                }
                this@LoginActivity.finish()
            } else {
                binding.progressBar.visibility = View.GONE
                binding.btnLogin.visibility = View.VISIBLE
            }
        }
        viewModel.onMessageLiveData.observe(this@LoginActivity) {
            noticeToast(it)
        }
        viewModel.onFailLiveData.observe(this@LoginActivity) {
            noticeToast(it)
        }
    }

    private fun selectSignUpAs() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@LoginActivity)
        builder.setTitle("Sign up as?")

        val user = arrayOf("Engineer (Worker)", "Company (Recruiter)")
        builder.setItems(user) { _, which ->
            when (which) {
                0 -> {
                    val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
                    intent.putExtra("level", 0)
                    startActivity(intent)
                }
                1 -> {
                    val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
                    intent.putExtra("level", 1)
                    startActivity(intent)
                }
            }
        }.show()
    }

    inner class MyTextWatcher(private val view: View) : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun afterTextChanged(editable: Editable) {
            when (view.id) {
                R.id.et_email -> valEmail(binding.inputLayoutEmail, binding.etEmail)
                R.id.et_password -> valPassword(binding.inputLayoutPassword, binding.etPassword)
            }
        }
    }

}