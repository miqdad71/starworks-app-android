package com.miqdad71.starworks.view.activities.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.ActivityCoreBinding
import com.miqdad71.starworks.view.fragments.HomeFragment
import com.miqdad71.starworks.view.fragments.JobsFragment
import com.miqdad71.starworks.view.fragments.SearchFragment
import com.miqdad71.starworks.view.fragments.engineer.ProfileEngineerFragment
import kotlinx.android.synthetic.main.activity_core.*

class EngineerMainActivity : AppCompatActivity() {
   private lateinit var binding: ActivityCoreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
//      bottomNavigationView.background = null
//      binding.bottomNavigationView.menu.getItem(2).isEnabled = false
//      binding.bottomNavigationView.background = null
        binding = DataBindingUtil.setContentView(this, R.layout.activity_core)

        val firstFragment = HomeFragment()
        val secondFragment = SearchFragment()
        val thirdFragment = ProfileEngineerFragment()
        val fourthFragment = JobsFragment()

        setCurrentFragment(firstFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.miHome -> setCurrentFragment(firstFragment)
                R.id.miSearch -> setCurrentFragment(secondFragment)
                R.id.miProfile -> setCurrentFragment(thirdFragment)
                R.id.miJobs -> setCurrentFragment(fourthFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, fragment)
                commit()
            }
}