package com.miqdad71.starworks.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.miqdad71.starworks.R
import com.miqdad71.starworks.view.fragments.FirstFragment
import com.miqdad71.starworks.view.fragments.FourthFragment
import com.miqdad71.starworks.view.fragments.SecondFragment
import com.miqdad71.starworks.view.fragments.ThirdFragment
import kotlinx.android.synthetic.main.activity_core.*

class CoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_core)
        bottomNavigationView.background = null
//        bottomNavigationView.menu.getItem(2).isEnabled = false

        val firstFragment = FirstFragment()
        val secondFragment = SecondFragment()
        val thirdFragment = ThirdFragment()
        val fourthFragment = FourthFragment()

        setCurrentFragment(firstFragment)

        bottomNavigationView.setOnNavigationItemReselectedListener {
            when(it.itemId){
                R.id.miHome -> setCurrentFragment(firstFragment)
                R.id.miSearch -> setCurrentFragment(secondFragment)
                R.id.miProfile -> setCurrentFragment(thirdFragment)
                R.id.miJobs -> setCurrentFragment(fourthFragment)
            }
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, fragment)
                commit()
            }
}