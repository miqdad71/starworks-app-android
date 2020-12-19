package com.miqdad71.starworks.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.miqdad71.starworks.R
import kotlinx.android.synthetic.main.activity_core.*

class CoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_core)
        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled = false
    }
}