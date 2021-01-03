package com.miqdad71.starworks.ui.activities.main.company

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.ActivityHireCompanyBinding

class HireCompanyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHireCompanyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hire_company)
    }
}