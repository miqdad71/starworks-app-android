//package com.miqdad71.starworks.view.detail_profile
//
//import android.os.Bundle
//import android.view.View
//import androidx.appcompat.app.AppCompatActivity
//import androidx.databinding.DataBindingUtil
//import com.miqdad71.starworks.R
//import com.miqdad71.starworks.databinding.ActivityProfileDetailBinding
//import com.miqdad71.starworks.util.SharedPreference
//import kotlinx.android.synthetic.main.activity_profile_detail.*
//import kotlinx.android.synthetic.main.fragment_profile_engineer.*
//
//class ProfileDetailActivity : AppCompatActivity(), View.OnClickListener {
//    private lateinit var binding: ActivityProfileDetailBinding
//    private lateinit var sharedPref: SharedPreference
//    private lateinit var userDetail: HashMap<String, String>
//    override fun onCreate(savedInstanceState: Bundle?) {
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_detail)
//        super.onCreate(savedInstanceState)
//
//        setToolbarActionBar()
//        initViewPager()
//
//        if (sharedPref.getLevelUser() == 0) {
//            binding.btnHire.visibility = View.GONE
//        } else {
//            binding.btnHire.visibility = View.VISIBLE
//        }
//    }
//
//    private fun setToolbarActionBar() {
//        setSupportActionBar(binding.toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.title = "Profile Detail"
//        binding.toolbar.setNavigationOnClickListener {
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
//        binding.tabLayout.setupWithViewPager(view_pager)
//        val adapter = ViewPagerAdapter(supportFragmentManager)
//
//        adapter.addFrag(DetailProfilePortfolioFragment(), "Portfolio")
//        adapter.addFrag(DetailProfileExperienceFragment(), "Experience")
//        binding.viewPager.adapter = adapter
//    }
//
//    override fun onStart() {
//        super.onStart()
//        sharedPref.createInDetail(1)
//    }
//}