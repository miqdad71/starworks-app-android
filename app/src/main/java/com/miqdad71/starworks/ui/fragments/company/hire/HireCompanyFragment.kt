package com.miqdad71.starworks.ui.fragments.company.hire

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.FragmentHireCompanyBinding
import com.miqdad71.starworks.ui.base.BaseFragmentCoroutine
import com.miqdad71.starworks.ui.fragments.company.hire.approve.ApproveFragment
import com.miqdad71.starworks.ui.fragments.company.hire.reject.RejectFragment
import com.miqdad71.starworks.ui.fragments.company.hire.wait.WaitFragment
import com.miqdad71.starworks.util.ViewPagerAdapter

class HireCompanyFragment : BaseFragmentCoroutine<FragmentHireCompanyBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.fragment_hire_company
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbarActionBar()
        setContentViewHiring()
    }

    private fun setToolbarActionBar() {
        val tb = (activity as AppCompatActivity)

        tb.setSupportActionBar(binding.toolbar)
        tb.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        tb.supportActionBar?.title = "Hiring Project"
    }

    private fun setContentViewHiring() {
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        val adapter = ViewPagerAdapter(parentFragmentManager)

        adapter.addFrag(WaitFragment(), "Wait")
        adapter.addFrag(ApproveFragment(), "Approve")
        adapter.addFrag(RejectFragment(), "Reject")
        binding.viewPager.adapter = adapter
    }
}
