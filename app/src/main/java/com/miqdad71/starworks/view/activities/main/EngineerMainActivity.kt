package com.miqdad71.starworks.view.activities.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.ActivityEngineerMainBinding
import com.miqdad71.starworks.view.fragments.engineer.EngineerHomeFragment
import com.miqdad71.starworks.view.fragments.engineer.EngineerJobsFragment
import com.miqdad71.starworks.view.fragments.engineer.EngineerSearchFragment
import com.miqdad71.starworks.view.fragments.engineer.EngineerProfileEngineerFragment
import kotlinx.android.synthetic.main.activity_engineer_main.*

class EngineerMainActivity : AppCompatActivity() {
   private lateinit var binding: ActivityEngineerMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
//      bottomNavigationView.background = null
//      binding.bottomNavigationView.menu.getItem(2).isEnabled = false
//      binding.bottomNavigationView.background = null
        binding = DataBindingUtil.setContentView(this, R.layout.activity_engineer_main)

        val firstFragment = EngineerHomeFragment()
        val secondFragment = EngineerSearchFragment()
        val thirdFragment = EngineerProfileEngineerFragment()
        val fourthFragment = EngineerJobsFragment()

        setCurrentFragment(firstFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.miHome -> setCurrentFragment(firstFragment)
                R.id.miSearch -> setCurrentFragment(secondFragment)
                R.id.miProfile -> setCurrentFragment(thirdFragment)
                R.id.miProject -> setCurrentFragment(fourthFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragmentEngineer, fragment)
                commit()
            }
}