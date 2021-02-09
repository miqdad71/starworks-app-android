package com.miqdad71.starworks.ui.activity.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.ActivitySignUpBinding
import com.miqdad71.starworks.ui.activity.OnboardActivity
import com.miqdad71.starworks.ui.activity.login.LoginActivity
import com.miqdad71.starworks.ui.base.BaseActivityCoroutine
import com.miqdad71.starworks.util.form_validate.ValidateAccount.Companion.valCompany
import com.miqdad71.starworks.util.form_validate.ValidateAccount.Companion.valEmail
import com.miqdad71.starworks.util.form_validate.ValidateAccount.Companion.valName
import com.miqdad71.starworks.util.form_validate.ValidateAccount.Companion.valPassConf
import com.miqdad71.starworks.util.form_validate.ValidateAccount.Companion.valPassword
import com.miqdad71.starworks.util.form_validate.ValidateAccount.Companion.valPhoneNumber
import com.miqdad71.starworks.util.form_validate.ValidateAccount.Companion.valPosition
import kotlinx.coroutines.*

class SignUpActivity : BaseActivityCoroutine<ActivitySignUpBinding>(), View.OnClickListener {
    private lateinit var viewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_sign_up
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        if (intent.getIntExtra("level", 0) == 1) {
            binding.clCompany.visibility = View.VISIBLE
        } else {
            binding.clCompany.visibility = View.GONE
        }

        initTextWatcher()
        setViewModel()
        subscribeLiveData()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_sign_up -> {
                if (intent.getIntExtra("level", 0) == 0) {
                    when {
                        !valName(binding.inputLayoutName, binding.etName) -> {}
                        !valEmail(binding.inputLayoutEmail, binding.etEmail) -> {}
                        !valPhoneNumber(binding.inputLayoutPhoneNumber, binding.etPhoneNumber) -> {}
                        !valPassword(binding.inputLayoutPassword, binding.etPassword) -> {}
                        !valPassConf(binding.inputLayoutPasswordConfirm, binding.etPasswordConfirm, binding.etPassword) -> {}
                        else -> {
                            viewModel.serviceEngineerApi(
                                acName = binding.etName.text.toString(),
                                acEmail = binding.etEmail.text.toString(),
                                acPhone = binding.etPhoneNumber.text.toString(),
                                acPassword = binding.etPassword.text.toString()
                            )
                        }
                    }
                } else {
                    when {
                        !valName(binding.inputLayoutName, binding.etName) -> {}
                        !valEmail(binding.inputLayoutEmail, binding.etEmail) -> {}
                        !valCompany(binding.inputLayoutCompany, binding.etCompany) -> {}
                        !valPosition(binding.inputLayoutPosition, binding.etPosition) -> {}
                        !valPhoneNumber(binding.inputLayoutPhoneNumber, binding.etPhoneNumber) -> {}
                        !valPassword(binding.inputLayoutPassword, binding.etPassword) -> {}
                        !valPassConf(binding.inputLayoutPasswordConfirm, binding.etPasswordConfirm, binding.etPassword) -> {}
                        else -> {
                            viewModel.serviceCompanyApi(
                                acName = binding.etName.text.toString(),
                                acEmail = binding.etEmail.text.toString(),
                                acPhone = binding.etPhoneNumber.text.toString(),
                                acPassword = binding.etPassword.text.toString(),
                                cnCompany = binding.etCompany.text.toString(),
                                cnPosition = binding.etPosition.text.toString()
                            )
                        }
                    }
                }
            }
            R.id.tv_login -> {
                this@SignUpActivity.finish()
            }
        }
    }

    private fun initTextWatcher() {
        binding.etName.addTextChangedListener(MyTextWatcher(binding.etName))
        binding.etEmail.addTextChangedListener(MyTextWatcher(binding.etEmail))

        if (intent.getIntExtra("level", 0) == 1) {
            binding.etCompany.addTextChangedListener(MyTextWatcher(binding.etCompany))
            binding.etPosition.addTextChangedListener(MyTextWatcher(binding.etPosition))
        }

        binding.etPhoneNumber.addTextChangedListener(MyTextWatcher(binding.etPhoneNumber))
        binding.etPassword.addTextChangedListener(MyTextWatcher(binding.etPassword))
        binding.etPasswordConfirm.addTextChangedListener(MyTextWatcher(binding.etPasswordConfirm))
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this@SignUpActivity).get(SignUpViewModel::class.java)
        viewModel.setService(createApi(this@SignUpActivity))
    }

    private fun subscribeLiveData() {

        viewModel.isLoadingLiveData.observe(this@SignUpActivity) {
            binding.btnSignUp.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        }

        viewModel.onSuccessLiveData.observe(this@SignUpActivity) {
            if (it.isNotEmpty()) {
                binding.progressBar.visibility = View.GONE
                binding.btnSignUp.visibility = View.VISIBLE
                noticeToast(it)
                if (intent.getIntExtra("onBoard", 0) == 1) {
                    intents<LoginActivity>(this@SignUpActivity)
                    this@SignUpActivity.finish()
                } else {
                    this@SignUpActivity.finish()
                }

            } else {
                binding.progressBar.visibility = View.GONE
                binding.btnSignUp.visibility = View.VISIBLE
            }
        }

        viewModel.onFailLiveData.observe(this@SignUpActivity) {
            noticeToast(it)
        }

    }

    inner class MyTextWatcher(private val view: View) : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun afterTextChanged(editable: Editable) {
            when (view.id) {
                R.id.et_name -> valName(binding.inputLayoutName, binding.etName)
                R.id.et_email -> valEmail(binding.inputLayoutEmail, binding.etEmail)
                R.id.et_company -> valCompany(binding.inputLayoutCompany, binding.etCompany)
                R.id.et_position -> valPosition(binding.inputLayoutPosition, binding.etPosition)
                R.id.et_phone_number -> valPhoneNumber(binding.inputLayoutPhoneNumber, binding.etPhoneNumber)
                R.id.et_password -> valPassword(binding.inputLayoutPassword, binding.etPassword)
                R.id.et_password_confirm -> valPassConf(binding.inputLayoutPasswordConfirm, binding.etPasswordConfirm, binding.etPassword)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        intents<OnboardActivity>(this@SignUpActivity)
        this@SignUpActivity.finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}


