package com.miqdad71.starworks.view.activities.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.ActivitySignUpBinding
import com.miqdad71.starworks.view.activities.login.LoginActivity

class SignUpActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        binding.tvLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when (v?.id) {
//            R.id.btn_sign_up -> {
//            }
                R.id.tv_login -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}