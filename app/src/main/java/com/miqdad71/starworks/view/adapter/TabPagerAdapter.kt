package com.miqdad71.starworks.view.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.miqdad71.starworks.R
import com.miqdad71.starworks.view.fragments.ExperienceFragment
import com.miqdad71.starworks.view.fragments.PortofolioFragment

class TabPagerAdapter(private val mContext: Context, fragmentManager: FragmentManager): FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    val tabTitle = intArrayOf(R.string.portfolio_tab_title, R.string.experience_tab_title)

    override fun getCount(): Int = tabTitle.size

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = PortofolioFragment()
            1 -> fragment = ExperienceFragment()
        }
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(tabTitle[position])

    }


}