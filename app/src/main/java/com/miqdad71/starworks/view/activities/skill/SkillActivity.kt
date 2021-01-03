package com.miqdad71.starworks.view.activities.skill

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.ActivitySkillBinding

class SkillActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySkillBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_skill)
        super.onCreate(savedInstanceState)

        setToolbarActionBar()
    }

    private fun setToolbarActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Skill"
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ln_back -> {
                this@SkillActivity.finish()
            }
        }
    }
}