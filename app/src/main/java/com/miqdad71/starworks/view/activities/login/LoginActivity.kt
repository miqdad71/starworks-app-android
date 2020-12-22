package com.miqdad71.starworks.view.activities.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.ActivityLoginBinding
import com.miqdad71.starworks.view.activities.CoreActivity
import com.miqdad71.starworks.view.activities.forgetpassword.ForgetPasswordVerifyActivity
import com.miqdad71.starworks.view.activities.signup.SignUpActivity

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding

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
        
    }

    override fun onClick(v: View?){

        when (v?.id) {
            R.id.tv_forget_password -> {
                val intent = Intent(this, ForgetPasswordVerifyActivity::class.java)
                startActivity(intent)
            }
            R.id.login -> {
                val intent = Intent(this, CoreActivity::class.java)
                startActivity(intent)
            }
            R.id.tv_sign_up -> {
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
            }
        }
    }
}