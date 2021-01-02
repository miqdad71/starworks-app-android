package com.miqdad71.starworks.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.miqdad71.starworks.view.fragments.engineer.ExperienceEngineerFragment
import com.miqdad71.starworks.view.fragments.engineer.PortofolioEngineerFragment

class EngineerPagerAdapter(fragmentManager: FragmentManager): FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    val tabTitle = arrayOf("Portfolio", "Experience")
    private val fragment = arrayOf(PortofolioEngineerFragment(), ExperienceEngineerFragment())

    override fun getCount(): Int = tabTitle.size

    override fun getItem(position: Int): Fragment {
        return fragment[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return tabTitle[position]
    }


}