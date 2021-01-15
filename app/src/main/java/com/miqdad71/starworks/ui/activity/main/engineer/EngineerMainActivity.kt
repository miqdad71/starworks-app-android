package com.miqdad71.starworks.ui.activity.main.engineer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.ActivityEngineerMainBinding
import com.miqdad71.starworks.ui.fragments.engineer.hire.HireEngineerFragment
import com.miqdad71.starworks.ui.fragments.engineer.home.HomeEngineerFragment
import com.miqdad71.starworks.ui.fragments.engineer.profile.ProfileEngineerFragment
import com.miqdad71.starworks.ui.fragments.engineer.search.SearchEngineerFragment
import kotlinx.android.synthetic.main.activity_engineer_main.*


class EngineerMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEngineerMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_engineer_main)

        val firstFragment = HomeEngineerFragment()
        val secondFragment = SearchEngineerFragment()
        val thirdFragment = ProfileEngineerFragment()
        val fourthFragment = HireEngineerFragment()

        setCurrentFragment(firstFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.miHome -> setCurrentFragment(firstFragment)
                R.id.miSearch -> setCurrentFragment(secondFragment)
                R.id.miProfile -> setCurrentFragment(thirdFragment)
                R.id.miProject -> setCurrentFragment(fourthFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment?) {
        if (fragment != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.flFragmentEngineer, fragment)
                .commit()
        }
    }
}