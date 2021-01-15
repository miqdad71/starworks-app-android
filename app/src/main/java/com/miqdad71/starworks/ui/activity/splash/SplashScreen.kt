package com.miqdad71.starworks.ui.activity.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.miqdad71.starworks.R
import com.miqdad71.starworks.ui.activity.OnboardActivity
import com.miqdad71.starworks.ui.activity.main.company.CompanyMainActivity
import com.miqdad71.starworks.ui.activity.main.engineer.EngineerMainActivity
import com.miqdad71.starworks.util.SharedPreference


class SplashScreen : AppCompatActivity() {
    private lateinit var sharedPref: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )

        sharedPref = SharedPreference(this)

        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed({

            if (sharedPref.getIsLogin() && sharedPref.getLevelUser() == 0){
                startActivity(Intent(applicationContext, EngineerMainActivity::class.java))
            } else {
                startActivity(Intent(applicationContext, CompanyMainActivity::class.java))
            }
            finish()
        }, 3000)
    }
}