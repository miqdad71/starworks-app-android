package com.miqdad71.starworks.ui.adapter.engineer

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.miqdad71.starworks.ui.fragments.engineer.profile.ExperienceEngineerFragment
import com.miqdad71.starworks.ui.fragments.engineer.profile.PortfolioEngineerFragment

class EngineerPagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    val tabTitle = arrayOf("Portfolio", "Experience")
    private val fragment = arrayOf(PortfolioEngineerFragment(), ExperienceEngineerFragment())

    override fun getCount(): Int = tabTitle.size

    override fun getItem(position: Int): Fragment {
        return fragment[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return tabTitle[position]
    }


}