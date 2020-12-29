package com.miqdad71.starworks.view.fragments.engineer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.FragmentHomeBinding
import com.miqdad71.starworks.view.model.EngineerModel

class EngineerHomeFragment : Fragment(R.layout.fragment_home), View.OnClickListener {

    private lateinit var rootView: View
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)

//      binding fragments
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root

    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.accountModel = AccountModel(ac_name = userDetail[AC_NAME])
//        setupWebDevRecyclerView()
//        setupAndroidDevRecyclerView()

    }


}