package com.miqdad71.starworks.ui.activity.main.company

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.ActivityAddProjectBinding

class AddProjectActivity() : AppCompatActivity(), Parcelable {
    private lateinit var binding: ActivityAddProjectBinding

    constructor(parcel: Parcel) : this() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_project)
        super.onCreate(savedInstanceState)

        setToolbarActionBar()
    }

    private fun setToolbarActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Profile Hire"
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<AddProjectActivity> {
        override fun createFromParcel(parcel: Parcel): AddProjectActivity {
            return AddProjectActivity(parcel)
        }

        override fun newArray(size: Int): Array<AddProjectActivity?> {
            return arrayOfNulls(size)
        }
    }
}