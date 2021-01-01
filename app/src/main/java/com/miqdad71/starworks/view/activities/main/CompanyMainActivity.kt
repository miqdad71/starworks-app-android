package com.miqdad71.starworks.view.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.ActivityCompanyMainBinding
import com.miqdad71.starworks.view.fragments.company.ProfileCompanyFragment
import com.miqdad71.starworks.view.fragments.engineer.EngineerHomeFragment
import com.miqdad71.starworks.view.fragments.engineer.EngineerJobsFragment
//import com.miqdad71.starworks.view.fragments.engineer.EngineerProfileEngineerFragment
import com.miqdad71.starworks.view.fragments.engineer.EngineerSearchFragment
import kotlinx.android.synthetic.main.activity_engineer_main.*

class CompanyMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCompanyMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_company_main)

        val firstFragment = EngineerHomeFragment()
        val secondFragment = EngineerSearchFragment()
        val thirdFragment = ProfileCompanyFragment()
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
            replace(R.id.flFragmentCompany, fragment)
            commit()
        }
}
