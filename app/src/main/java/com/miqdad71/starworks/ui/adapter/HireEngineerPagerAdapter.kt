package com.miqdad71.starworks.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.miqdad71.starworks.ui.fragments.engineer.hire.BiddingProjectFragment
import com.miqdad71.starworks.ui.fragments.engineer.hire.EngineerProjectFragment
import com.miqdad71.starworks.ui.fragments.engineer.profile.ExperienceEngineerFragment
import com.miqdad71.starworks.ui.fragments.engineer.profile.PortofolioEngineerFragment

class HireEngineerPagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    val tabTitle = arrayOf("Your Project", "Project Bidding")
    private val fragment = arrayOf(BiddingProjectFragment(), EngineerProjectFragment())

    override fun getCount(): Int = tabTitle.size

    override fun getItem(position: Int): Fragment {
        return fragment[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return tabTitle[position]
    }


}