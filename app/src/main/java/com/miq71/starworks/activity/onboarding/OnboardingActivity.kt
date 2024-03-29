package com.miq71.starworks.activity.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.miq71.starworks.R
import com.miq71.starworks.base.BaseActivity
import com.miq71.starworks.activity.login.LoginActivity
import com.miq71.starworks.activity.signup.SignUpActivity
import com.miq71.starworks.databinding.ActivityOnboardingBinding

class OnboardingActivity : BaseActivity<ActivityOnboardingBinding>(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_onboarding
        super.onCreate(savedInstanceState)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> {
                intents<LoginActivity>(this@OnboardingActivity)
            }
            R.id.btn_sign_up -> {
                selectSignUpAs()
            }
        }
    }

    private fun selectSignUpAs() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@OnboardingActivity)
        builder.setTitle("Sign up as?")

        val user = arrayOf("Engineer (Worker)", "Company (Recruiter)")
        builder.setItems(user) { _, which ->
            when (which) {
                0 -> {
                    val intent = Intent(this@OnboardingActivity, SignUpActivity::class.java)
                    intent.putExtra("level", 0)
                    intent.putExtra("onBoard", 1)
                    startActivity(intent)
                    this@OnboardingActivity.finish()
                }
                1 -> {
                    val intent = Intent(this@OnboardingActivity, SignUpActivity::class.java)
                    intent.putExtra("level", 1)
                    intent.putExtra("onBoard", 1)
                    startActivity(intent)
                    this@OnboardingActivity.finish()
                }
            }
        }.show()
    }
}