package com.miqdad71.starworks.ui.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.miqdad71.starworks.util.SharedPreference

abstract class BaseFragment<FragmentBinding : ViewDataBinding> : Fragment() {
    protected lateinit var binding: FragmentBinding
    protected lateinit var sharedPref: SharedPreference
    protected lateinit var userDetail: HashMap<String, String>
    protected var setLayout: Int? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, setLayout!!, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = SharedPreference(view.context)
        userDetail = sharedPref.getAccountUser()
    }

    protected inline fun <reified ClassActivity> intents(activity: FragmentActivity?) {
        activity?.startActivity(Intent(activity, ClassActivity::class.java))
    }

    protected fun logoutConf(activity: FragmentActivity?) {
        val dialog = activity?.let {
            AlertDialog
                .Builder(it)
                .setTitle("Notice!")
                .setMessage("Are you sure to logout?")
                .setPositiveButton("OK") { _, _ ->
                    sharedPref.accountLogout()
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
        }

        dialog?.show()
    }
}