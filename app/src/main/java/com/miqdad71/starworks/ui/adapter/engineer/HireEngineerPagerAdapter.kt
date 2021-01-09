//package com.miqdad71.starworks.ui.adapter
//
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentManager
//import androidx.fragment.app.FragmentStatePagerAdapter
//import com.miqdad71.starworks.ui.fragments.engineer.hire.HireEngineerFragment
//import com.miqdad71.starworks.ui.fragments.engineer.hire.EngineerProjectFragment
//
//class HireEngineerPagerAdapter(fragmentManager: FragmentManager) :
//    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
//
//    val tabTitle = arrayOf("Your Project", "Project Bidding")
//    private val fragment = arrayOf(EngineerProjectFragment(), HireEngineerFragment())
//
//    override fun getCount(): Int = tabTitle.size
//
//    override fun getItem(position: Int): Fragment {
//        return fragment[position]
//    }
//
//    override fun getPageTitle(position: Int): CharSequence {
//        return tabTitle[position]
//    }
//
//
//}