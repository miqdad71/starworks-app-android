package com.miqdad71.starworks.ui.activity.main.company

import android.os.Bundle
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.ActivitySettingsBinding
import com.miqdad71.starworks.ui.base.BaseActivity
import com.miqdad71.starworks.ui.fragments.settings.SettingsFragment

class SettingsActivity : BaseActivity<ActivitySettingsBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_settings
        super.onCreate(savedInstanceState)
        setToolbarActionBar()

        val ids: Int = if (sharedPref.getLevelUser() == 0) {
            sharedPref.getIdEngineer()
        } else {
            sharedPref.getIdCompany()
        }

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment(ids, sharedPref.getIdAccount(), sharedPref.getLevelUser()))
                .commit()
        }
    }

    private fun setToolbarActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Edit Profile"
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}