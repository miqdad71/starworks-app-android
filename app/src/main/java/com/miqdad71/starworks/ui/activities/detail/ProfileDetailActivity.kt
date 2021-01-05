package com.miqdad71.starworks.ui.activities.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.ActivityProfileDetailBinding
import com.miqdad71.starworks.ui.activities.main.company.HireCompanyActivity

class ProfileDetailActivity : AppCompatActivity(), View.OnClickListener {
    
    private lateinit var binding: ActivityProfileDetailBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_detail)
        setToolbarActionBar()
//        initViewPager()

//        if (sharedPref.getLevelUser() == 0) {
//            binding.btnHire.visibility = View.GONE
//        } else {
//            binding.btnHire.visibility = View.VISIBLE
//        }
    }

    private fun setToolbarActionBar() {
        setSupportActionBar(binding.toolbar)
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//        )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Profile Detail"
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_hire -> {
                val intent = Intent(this, HireCompanyActivity::class.java)
                startActivity(intent)
            }
        }
    }

//    private fun initViewPager() {
//        bind.tabLayout.setupWithViewPager(view_pager)
//        val adapter = ViewPagerAdapter(supportFragmentManager)
//
//        adapter.addFrag(DetailProfilePortfolioFragment(), "Portfolio")
//        adapter.addFrag(DetailProfileExperienceFragment(), "Experience")
//        bind.viewPager.adapter = adapter
//    }
//
//    override fun onStart() {
//        super.onStart()
//        sharedPref.createInDetail(1)
//    }
}

//class ProfileDetailActivity : BaseActivity<ActivityProfileDetailBinding>(), View.OnClickListener {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        setLayout = R.layout.activity_profile_detail
//        super.onCreate(savedInstanceState)
//
//        setToolbarActionBar()
//        initViewPager()
//
//        if (sharedPref.getLevelUser() == 0) {
//            bind.btnHire.visibility = View.GONE
//        } else {
//            bind.btnHire.visibility = View.VISIBLE
//        }
//    }
//
//    private fun setToolbarActionBar() {
//        setSupportActionBar(bind.toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.title = "Profile Detail"
//        bind.toolbar.setNavigationOnClickListener {
//            onBackPressed()
//        }
//    }
//
//    override fun onClick(v: View?) {
//        when (v?.id) {
//            R.id.btn_hire -> {
//                intents<HireActivity>(this@ProfileDetailActivity)
//            }
//        }
//    }
//
//    private fun initViewPager() {
//        bind.tabLayout.setupWithViewPager(view_pager)
//        val adapter = ViewPagerAdapter(supportFragmentManager)
//
//        adapter.addFrag(DetailProfilePortfolioFragment(), "Portfolio")
//        adapter.addFrag(DetailProfileExperienceFragment(), "Experience")
//        bind.viewPager.adapter = adapter
//    }
//
//    override fun onStart() {
//        super.onStart()
//        sharedPref.createInDetail(1)
//    }
//}