package com.miqdad71.starworks.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.ActivityMainBinding
import com.miqdad71.starworks.ui.activity.login.LoginActivity

class OnboardActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btnLogin.setOnClickListener(this)
        binding.btnCompany.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> {
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("level", 0)
                startActivity(intent)
                this.finish()

            }
            R.id.btn_company -> {
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("level", 1)
                startActivity(intent)
                this.finish()
            }

        }
    }

}