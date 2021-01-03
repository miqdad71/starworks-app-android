package com.miqdad71.starworks.view.fragments.engineer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.FragmentProfileEngineerBinding
import com.miqdad71.starworks.view.activities.skill.SkillActivity
import com.miqdad71.starworks.view.adapter.EngineerPagerAdapter
import com.miqdad71.starworks.view.webview.WebViewActivity

class ProfileEngineerFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentProfileEngineerBinding
    private lateinit var adapter: EngineerPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//      binding fragments
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile_engineer, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvGithub.setOnClickListener(this)
        binding.ivAddSkill.setOnClickListener(this)
        adapter = EngineerPagerAdapter(requireFragmentManager())
        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    override fun onResume() {
        super.onResume()
        adapter = EngineerPagerAdapter(requireFragmentManager())
        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.tv_github -> {
                val intent = Intent(v.context, WebViewActivity::class.java)
                startActivity(intent)
            }
            R.id.iv_add_skill -> {
                val intent = Intent(v.context, SkillActivity::class.java)
                startActivity(intent)
            }
        }
    }

//    private fun dataSkill() {
//        skillModel = ArrayList()
//        skillModel.add(SkillModel(sk_skill_name = "PHP"))
//        skillModel.add(SkillModel(sk_skill_name = "Javascript"))
//        skillModel.add(SkillModel(sk_skill_name = "Dart"))
//        skillModel.add(SkillModel(sk_skill_name = "Kotlin"))
//        skillModel.add(SkillModel(sk_skill_name = "Java"))
//        skillModel.add(SkillModel(sk_skill_name = "HTML5"))
//        skillModel.add(SkillModel(sk_skill_name = "CSS3"))
//        skillModel.add(SkillModel(sk_skill_name = "C++"))
//        skillModel.add(SkillModel(sk_skill_name = "C#"))
//        skillModel.add(SkillModel(sk_skill_name = "C"))
//        skillModel.add(SkillModel(sk_skill_name = "Node JS"))
//        skillModel.add(SkillModel(sk_skill_name = "Express JS"))
//        skillModel.add(SkillModel(sk_skill_name = "React JS"))
//        skillModel.add(SkillModel(sk_skill_name = "Vue JS"))
//        skillModel.add(SkillModel(sk_skill_name = "Angular JS"))
//        skillModel.add(SkillModel(sk_skill_name = "CodeIgniter"))
//        skillModel.add(SkillModel(sk_skill_name = "Laravel"))
//        skillModel.add(SkillModel(sk_skill_name = "Spring"))
//        skillModel.add(SkillModel(sk_skill_name = "Golang"))
//        skillModel.add(SkillModel(sk_skill_name = "Python"))
//        skillModel.add(SkillModel(sk_skill_name = "Flutter"))
//    }
//    override fun onStart() {
//        super.onStart()
//        sharedPref.createInDetail(0)
//    }
}
