package com.miqdad71.starworks.ui.activity.main.engineer

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.ActivityEngineerMainBinding
import com.miqdad71.starworks.ui.activity.experience.ExperienceActivity
import com.miqdad71.starworks.ui.activity.portfolio.PortfolioActivity
import com.miqdad71.starworks.ui.fragments.engineer.hire.HireEngineerFragment
import com.miqdad71.starworks.ui.fragments.engineer.home.HomeEngineerFragment
import com.miqdad71.starworks.ui.fragments.engineer.profile.ProfileEngineerFragment
import com.miqdad71.starworks.ui.fragments.engineer.search.SearchEngineerFragment
import kotlinx.android.synthetic.main.activity_engineer_main.*


class EngineerMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEngineerMainBinding

    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim) }

    private var clicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_engineer_main)
        super.onCreate(savedInstanceState)

        binding.faEngProfile.setOnClickListener {
            onAddButtonClicked()
        }
        binding.faAddPortfolio.setOnClickListener{
            Toast.makeText(this, "Add Portfolio", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, PortfolioActivity::class.java)
            startActivity(intent)
        }
        binding.faAddExperience.setOnClickListener {
            Toast.makeText(this, "Add Experience", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ExperienceActivity::class.java)
            startActivity(intent)
        }

        val firstFragment = HomeEngineerFragment()
        val secondFragment = SearchEngineerFragment()
        val thirdFragment = HireEngineerFragment()
        val fourthFragment = ProfileEngineerFragment()

        setCurrentFragment(firstFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.miHome -> setCurrentFragment(firstFragment)
                R.id.miSearch -> setCurrentFragment(secondFragment)
                R.id.miProject -> setCurrentFragment(thirdFragment)
                R.id.miProfile -> setCurrentFragment(fourthFragment)
            }
            true
        }
    }
    private fun onAddButtonClicked(){
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun  setVisibility(clicked: Boolean){
        if(!clicked){
            fa_add_portfolio.visibility = View.VISIBLE
            fa_add_experience.visibility = View.VISIBLE
        } else {
            fa_add_portfolio.visibility = View.INVISIBLE
            fa_add_experience.visibility = View.INVISIBLE
        }
    }

    private fun setAnimation(clicked: Boolean){
        if (!clicked){
            fa_add_portfolio.startAnimation(fromBottom)
            fa_add_experience.startAnimation(fromBottom)
            fa_eng_profile.startAnimation(rotateOpen)
        } else {
            fa_add_portfolio.startAnimation(toBottom)
            fa_add_experience.startAnimation(toBottom)
            fa_eng_profile.startAnimation(rotateClose)
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