package com.miqdad71.starworks.ui.activity.forgetpassword.reset_password

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.ActivityForgetPasswordBinding
import com.miqdad71.starworks.ui.base.BaseActivityCoroutine
import com.miqdad71.starworks.util.form_validate.ValidateAccount.Companion.valPassConf
import com.miqdad71.starworks.util.form_validate.ValidateAccount.Companion.valPassword

class ResetPasswordActivity : BaseActivityCoroutine<ActivityForgetPasswordBinding>(), View.OnClickListener {
    private lateinit var viewModel: ResetPasswordViewModel
    private var acId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_forget_password
        super.onCreate(savedInstanceState)
        acId = intent.getIntExtra("ac_id", 0)

        initTextWatcher()
        setViewModel()
        subscribeLiveData()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_reset_password -> {
                when {
                    !valPassword(binding.inputLayoutNewPassword, binding.etNewPassword) -> {}
                    !valPassConf(
                        binding.inputLayoutPasswordConfirm,
                        binding.etPasswordConfirm,
                        binding.etNewPassword
                    ) -> {}
                    else -> {
                        viewModel.serviceUpdate(
                            acId = acId!!,
                            acPassword = binding.etNewPassword.text.toString()
                        )
                    }
                }
            }
            R.id.ln_back -> {
                this@ResetPasswordActivity.finish()
            }
        }
    }

    private fun initTextWatcher() {
        binding.etNewPassword.addTextChangedListener(MyTextWatcher(binding.etNewPassword))
        binding.etPasswordConfirm.addTextChangedListener(MyTextWatcher(binding.etPasswordConfirm))
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this@ResetPasswordActivity).get(ResetPasswordViewModel::class.java)
        viewModel.setService(createApi(this@ResetPasswordActivity))
    }

    private fun subscribeLiveData() {
        viewModel.isLoadingLiveData.observe(this@ResetPasswordActivity) {
            binding.btnResetPassword.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        }

        viewModel.onSuccessLiveData.observe(this@ResetPasswordActivity) {
            if (it) {
                binding.progressBar.visibility = View.GONE
                binding.btnResetPassword.visibility = View.VISIBLE

                this@ResetPasswordActivity.finish()
            } else {
                binding.progressBar.visibility = View.GONE
                binding.btnResetPassword.visibility = View.VISIBLE
            }
        }

        viewModel.onMessageLiveData.observe(this@ResetPasswordActivity) {
            noticeToast(it)
        }

        viewModel.onFailLiveData.observe(this@ResetPasswordActivity) {
            noticeToast(it)
        }
    }

    inner class MyTextWatcher(private val view: View) : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun afterTextChanged(editable: Editable) {
            when (view.id) {
                R.id.et_password -> valPassword(
                    binding.inputLayoutNewPassword,
                    binding.etNewPassword
                )
                R.id.et_password_confirm -> valPassConf(
                    binding.inputLayoutPasswordConfirm,
                    binding.etPasswordConfirm,
                    binding.etNewPassword
                )
            }
        }
    }
}