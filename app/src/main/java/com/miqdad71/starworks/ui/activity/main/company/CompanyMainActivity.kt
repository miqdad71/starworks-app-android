package com.miqdad71.starworks.ui.activity.main.company

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.ActivityCompanyMainBinding
import com.miqdad71.starworks.ui.fragments.company.hire.HireCompanyFragment
import com.miqdad71.starworks.ui.fragments.company.home.HomeCompanyFragment
import com.miqdad71.starworks.ui.fragments.company.profile.ProfileCompanyFragment
import com.miqdad71.starworks.ui.fragments.company.project.ProjectCompanyFragment
import com.miqdad71.starworks.ui.fragments.company.search.SearchCompanyFragment
import kotlinx.android.synthetic.main.activity_engineer_main.*

class CompanyMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCompanyMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_company_main)
        super.onCreate(savedInstanceState)

        val firstFragment = HomeCompanyFragment()
        val secondFragment = SearchCompanyFragment()
        val thirdFragment = HireCompanyFragment()
        val fourthFragment = ProjectCompanyFragment()
        val fifthFragment = ProfileCompanyFragment()

        setCurrentFragment(firstFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.miHome -> setCurrentFragment(firstFragment)
                R.id.miSearch -> setCurrentFragment(secondFragment)
                R.id.miHire -> setCurrentFragment(thirdFragment)
                R.id.miProject -> setCurrentFragment(fourthFragment)
                R.id.miProfile -> setCurrentFragment(fifthFragment)

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
