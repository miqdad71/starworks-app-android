package com.miqdad71.starworks.view.activities.forgetpassword

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.ActivityForgetPasswordVerifyBinding

class ForgetPasswordVerifyActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityForgetPasswordVerifyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forget_password_verify)

        binding.lnBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
          R.id.btn_next -> {
              val intent = Intent(this, ForgetPasswordActivity::class.java)
              startActivity(intent)
            }
            R.id.ln_back -> {
                onBackPressed()
            }
        }
    }
}