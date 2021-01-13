package com.miqdad71.starworks.ui.activity.main.company

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.ActivityCompanyMainBinding
import com.miqdad71.starworks.ui.fragments.company.hire.HireCompanyFragment
import com.miqdad71.starworks.ui.fragments.company.home.HomeCompanyFragment
import com.miqdad71.starworks.ui.fragments.company.profile.ProfileCompanyFragment
import com.miqdad71.starworks.ui.fragments.company.project.ProjectCompanyFragment
import com.miqdad71.starworks.ui.fragments.company.search.SearchCompanyFragment
//import com.miqdad71.starworks.view.fragments.engineer.ProfileEngineerFragment
import kotlinx.android.synthetic.main.activity_engineer_main.*

class CompanyMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCompanyMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_company_main)

        val firstFragment = HomeCompanyFragment()
        val secondFragment = SearchCompanyFragment()
        val thirdFragment = ProfileCompanyFragment()
        val fourthFragment = HireCompanyFragment()
        val fifthFragment = ProjectCompanyFragment()

        setCurrentFragment(firstFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.miHome -> setCurrentFragment(firstFragment)
                R.id.miSearch -> setCurrentFragment(secondFragment)
                R.id.miProfile -> setCurrentFragment(thirdFragment)
                R.id.miHire -> setCurrentFragment(fourthFragment)
                R.id.miProject -> setCurrentFragment(fifthFragment)

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
