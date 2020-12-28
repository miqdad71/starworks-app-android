package com.miqdad71.starworks.view.activities.signup

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.ActivitySignUpBinding
import com.miqdad71.starworks.util.SharedPreference
import com.miqdad71.starworks.view.activities.main.EngineerMainActivity
import com.miqdad71.starworks.view.activities.login.LoginActivity
import com.miqdad71.starworks.view.dialog.Dialog
import com.miqdad71.starworks.view.model.EngineerModel

class SignUpActivity : AppCompatActivity() {

    companion object {
        const val FIELD_REQUIRED = "Field tidak boleh kosong"
        const val FIELD_DIGITS_ONLY = "Hanya boleh berisi numerik"
        const val FIELD_IS_NOT_VALID = "Email tidak valid"
        const val FIELD_MUST_MATCH = "Password harus sama"
    }
    private var engineerModel: EngineerModel = EngineerModel()
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        supportActionBar?.hide()
        window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )

        // sign-up action
        binding.btnSignUp.setOnClickListener {
            registration()
        }

        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun registration() {
        dialog = Dialog()
        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val passwordconfirm = binding.etPasswordConfirm.text.toString()
        val phonenumber = binding.etPhoneNumber.text.toString()
//        val position = binding.etPosition.text.toString()
//        val company = binding.etCompany.text.toString()

        if (name.isEmpty()) {
            binding.etName.error = FIELD_REQUIRED
            return
        }

        if (email.isEmpty()) {
            binding.etEmail.error = FIELD_IS_NOT_VALID
            return
        }

        if (password.isEmpty()) {
            binding.etPassword.error = FIELD_REQUIRED
            return
        }

        if (passwordconfirm.isEmpty()) {
            binding.etPasswordConfirm.error = FIELD_REQUIRED
            return
        }

        if (password != passwordconfirm) {
            binding.etPasswordConfirm.error = FIELD_MUST_MATCH
            return
        }

        if (phonenumber.isEmpty()) {
            binding.etPhoneNumber.error = FIELD_REQUIRED
            return
        }

        if (!TextUtils.isDigitsOnly(phonenumber)) {
            binding.etPhoneNumber.error = FIELD_DIGITS_ONLY
            return
        }
        saveData(name, email, password, phonenumber, true)

        dialog.dialog(this, "Register Successful") {
            val sendIntent = Intent(this, EngineerMainActivity::class.java)
            startActivity(sendIntent)
        }
    }

    private fun saveData(name: String, email: String, password: String, phone: String, isLogin: Boolean) {
        val userPreference = SharedPreference(this)

//        engineerModel.let {
//            it.name = name
//            it.email = email
//            it.password = password
//            it.phone = phone.toLong()
//            it.isLogin = isLogin
//        }

        engineerModel.name = name
        engineerModel.email = email
        engineerModel.password = password
        engineerModel.phone = phone.toLong()
        engineerModel.isLogin = isLogin
//        engineerModel.company = company
//        engineerModel.position = position

        userPreference.setEngineerPreference(engineerModel)
    }

}