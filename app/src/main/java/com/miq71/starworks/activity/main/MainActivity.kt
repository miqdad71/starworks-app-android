package com.miq71.starworks.activity.main

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.miq71.starworks.R
import com.miq71.starworks.base.BaseActivity
import com.miq71.starworks.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_main
        super.onCreate(savedInstanceState)
        sharedPref.checkIsLogin()

        setBottomNavigation()
    }

    private fun setBottomNavigation() {
        val navHostFragment = NavHostFragment.findNavController(supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!!)

        if (sharedPref.getLevelUser() == 0) {
            navHostFragment.setGraph(R.navigation.main_navigation_engineer)
            bind.bnvMain.inflateMenu(R.menu.menu_bottom_navigation_engineer)
        } else {
            navHostFragment.setGraph(R.navigation.main_navigation_company)
            bind.bnvMain.inflateMenu(R.menu.menu_bottom_navigation_company)
        }

        bind.bnvMain.setupWithNavController(navHostFragment)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = NavHostFragment.findNavController(supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!!)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}