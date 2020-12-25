package com.miqdad71.starworks.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.miqdad71.starworks.view.fragments.ExperienceFragment
import com.miqdad71.starworks.view.fragments.PortofolioFragment

abstract class TabPagerAdapter(fragment: FragmentManager): FragmentStatePagerAdapter(fragment, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    val fragment = arrayOf(ExperienceFragment(), PortofolioFragment())

    override fun getCount(): Int = fragment.size

    override fun getItem(position: Int): Fragment {
        return fragment[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Fragment A"
            1 -> "Fragment B"
            else -> ""
        }
    }

}