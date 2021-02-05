package com.miqdad71.starworks.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.ActivityMainBinding
import com.miqdad71.starworks.ui.activity.login.LoginActivity
import com.miqdad71.starworks.ui.activity.signup.SignUpActivity

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

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_to_login -> {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            R.id.btn_register -> {
                selectSignUpAs()
            }
        }
    }

    private fun selectSignUpAs() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@OnboardActivity)
        builder.setTitle("Sign up as?")

        val user = arrayOf("Engineer (Worker)", "Company (Recruiter)")
        builder.setItems(user) { _, which ->
            when (which) {
                0 -> {
                    val intent = Intent(this@OnboardActivity, SignUpActivity::class.java)
                    intent.putExtra("level", 0)
                    intent.putExtra("onBoard", 1)
                    startActivity(intent)
                    this@OnboardActivity.finish()
                }
                1 -> {
                    val intent = Intent(this@OnboardActivity, SignUpActivity::class.java)
                    intent.putExtra("level", 1)
                    intent.putExtra("onBoard", 1)
                    startActivity(intent)
                    this@OnboardActivity.finish()
                }
            }
        }.show()
    }

}