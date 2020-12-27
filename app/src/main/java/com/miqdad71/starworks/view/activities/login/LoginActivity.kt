package com.miqdad71.starworks.view.activities.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.textfield.TextInputLayout
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.ActivityLoginBinding
import com.miqdad71.starworks.util.SharedPreference
import com.miqdad71.starworks.view.activities.CoreActivity
import com.miqdad71.starworks.view.activities.forgetpassword.ForgetPasswordVerifyActivity
import com.miqdad71.starworks.view.activities.main.MainActivity
import com.miqdad71.starworks.view.activities.signup.SignUpActivity
import com.miqdad71.starworks.view.webview.WebViewActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    protected lateinit var sharedPref: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        supportActionBar?.hide()
        window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )
        binding.tvForgetPassword.setOnClickListener(this)
        binding.login.setOnClickListener(this)
        binding.tvSignUp.setOnClickListener(this)
        binding.tvHaveAccount.setOnClickListener(this)
    }

    override fun onClick(v: View?){
        val acLevel = intent.getIntExtra("level", 0)

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
                when {
                    binding.etEmail.text.toString().isEmpty() -> {
                        valTextLayout(input_layout_password)
                        valEditText(input_layout_email, et_email, "Please enter your email!")
                    }
                    binding.etPassword.text.toString().isEmpty() -> {
                        valTextLayout(input_layout_email)
                        valEditText(input_layout_password, et_password, "Please enter your password!")
                    }
                    else -> {
                        valTextLayout(input_layout_email)
                        valTextLayout(input_layout_password)

                        sharedPref.createAccountUser(acLevel, "Miqdad", et_email.text.toString(), "1234567890")
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        this@LoginActivity.finish()
                    }
                }
            }
        }
    }

    private fun valTextLayout(inputLayout: TextInputLayout) {
        inputLayout.isHelperTextEnabled = false
    }

    private fun valEditText(inputLayout: TextInputLayout, editText: EditText, hint: String) {
        val text = editText.text.toString().trim()

        if (text.isEmpty()) {
            inputLayout.isHelperTextEnabled = true
            inputLayout.helperText = hint
            editText.requestFocus()
        } else {
            inputLayout.isHelperTextEnabled = false
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        this@LoginActivity.finish()
//        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

}