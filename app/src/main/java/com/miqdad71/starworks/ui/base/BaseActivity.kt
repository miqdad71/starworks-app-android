package com.miqdad71.starworks.ui.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.miqdad71.starworks.util.SharedPreference

abstract class BaseActivity<ActivityBinding : ViewDataBinding> : AppCompatActivity() {
    protected lateinit var binding: ActivityBinding
    protected lateinit var sharedPref: SharedPreference
    protected lateinit var userDetail: HashMap<String, String>
    protected var setLayout: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@BaseActivity, setLayout!!)

        sharedPref = SharedPreference(this@BaseActivity)
        userDetail = sharedPref.getAccountUser()
    }

    protected inline fun <reified ClassActivity> intents(context: Context) {
        context.startActivity(Intent(context, ClassActivity::class.java))
    }
}