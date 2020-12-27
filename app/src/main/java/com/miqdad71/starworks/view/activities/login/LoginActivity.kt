package com.miqdad71.starworks.view.activities.login

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.ActivityLoginBinding
import com.miqdad71.starworks.util.SharedPreference
import com.miqdad71.starworks.view.activities.CoreActivity
import com.miqdad71.starworks.view.activities.forgetpassword.ForgetPasswordVerifyActivity
import com.miqdad71.starworks.view.activities.signup.SignUpActivity
import com.miqdad71.starworks.view.dialog.Dialog
import com.miqdad71.starworks.view.model.CompanyModel
import com.miqdad71.starworks.view.model.EngineerModel

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var preference: SharedPreference
    private lateinit var engineerModel: EngineerModel
    private lateinit var companyModel: CompanyModel
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        engineerModel = EngineerModel()

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

    override fun onClick(v: View?){

        when (v?.id) {
            R.id.tv_forget_password -> {
                val intent = Intent(this, ForgetPasswordVerifyActivity::class.java)
                startActivity(intent)
            }
            R.id.tv_sign_up -> {
                val intent = Intent(this, SignUpActivity::class.java)
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
        engineerModel = preference.getEngineerPreference(engineerModel)
        companyModel = preference.getCompanyPreference()

        if (email.isEmpty()) {
            binding.etEmail.error = SignUpActivity.FIELD_REQUIRED
            return
        }

        if (password.isEmpty()) {
            binding.etPassword.error = SignUpActivity.FIELD_REQUIRED
            return
        }

        if (engineerModel.email == email && engineerModel.password == password) {
            engineerModel.isLogin = true
            preference.getEngineerPreference(engineerModel)

            dialog.dialogCancel(this, "Login Successful") {
                val sendIntent = Intent(this, CoreActivity::class.java)
                startActivity(sendIntent)
                this.finish()
            }
        }
//        else if (companyModel.email == email && companyModel.password == password) {
//            companyModel.isLogin = true
//            SharedPreference.setCompanyPreference(companyModel)
//
//            dialog.dialogCancel(this, "Login Successful") {
//                val sendIntent = Intent(this, CompanyMainContentActivity::class.java)
//                startActivity(sendIntent)
//                activity?.finish()
//            }
//        }
        else {
            dialog.dialogCancel(this, "Email or Password Incorrect") { DialogInterface.BUTTON_NEGATIVE }
        }
    }

}