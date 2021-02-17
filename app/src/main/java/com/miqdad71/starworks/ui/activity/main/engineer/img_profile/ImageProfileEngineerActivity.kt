package com.miqdad71.starworks.ui.activity.main.engineer.img_profile

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.miqdad71.starworks.R
import com.miqdad71.starworks.data.remote.ApiClient
import com.miqdad71.starworks.databinding.ActivityImageProfileBinding
import com.miqdad71.starworks.ui.base.BaseActivityCoroutine
import com.miqdad71.starworks.util.FileHelper
import com.miqdad71.starworks.util.FileHelper.Companion.createPartFromFile

class ImageProfileEngineerActivity : BaseActivityCoroutine<ActivityImageProfileBinding>(), View.OnClickListener {
    private lateinit var viewModel: ImageProfileEngineerViewModel
    private var enId: Int? = null
    private var enProfile: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_image_profile
        super.onCreate(savedInstanceState)

        enId = intent.getIntExtra("en_id", 0)
        enProfile = intent.getStringExtra("en_profile")

        setToolbarActionBar()
        setDataFromIntent()
        setViewModel()
        subscribeLiveData()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ib_choose_image -> {
                pickImageFromGallery()
            }
            R.id.iv_image_view -> {
                pickImageFromGallery()
            }
            R.id.iv_image_load -> {
                pickImageFromGallery()
            }
            R.id.btn_update_image -> {
                if (enId != 0) {
                    if (pathImage == null) {
                        setResult(RESULT_OK)
                        this@ImageProfileEngineerActivity.finish()
                    } else {
                        viewModel.serviceUpdateImageEngineer(
                            enId = enId!!,
                            image = createPartFromFile(pathImage!!)
                        )
                    }
                } else {
                    noticeToast("Please login again!")
                }
            }
            R.id.ln_back -> {
                this@ImageProfileEngineerActivity.finish()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery()
                } else {
                    noticeToast("Permission denied...!")
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            binding.ibChooseImage.visibility = View.GONE
            binding.ivImageView.visibility = View.GONE
            binding.ivImageLoad.visibility = View.VISIBLE
            binding.ivImageLoad.setImageURI(data?.data)

            pathImage = FileHelper.getPathFromURI(this@ImageProfileEngineerActivity, data?.data!!)
        }
    }

    private fun setToolbarActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Profile"
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setDataFromIntent() {
        if (enId != 0) {
            binding.ibChooseImage.visibility = View.GONE
            binding.ivImageView.visibility = View.VISIBLE

            if (enProfile != null) {
                binding.imageUrl = ApiClient.BASE_URL_IMAGE + enProfile
            } else {
                binding.imageUrl = ApiClient.BASE_URL_IMAGE_DEFAULT_PROFILE_2
            }
        }
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this@ImageProfileEngineerActivity).get(ImageProfileEngineerViewModel::class.java)
        viewModel.setService(createApi(this@ImageProfileEngineerActivity))
    }

    private fun subscribeLiveData() {
        viewModel.isLoadingLiveData.observe(this@ImageProfileEngineerActivity) {
            binding.btnUpdateImage.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        }

        viewModel.onSuccessLiveData.observe(this@ImageProfileEngineerActivity) {
            if (it) {
                setResult(RESULT_OK)
                this@ImageProfileEngineerActivity.finish()

                binding.progressBar.visibility = View.GONE
                binding.btnUpdateImage.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.btnUpdateImage.visibility = View.VISIBLE
            }
        }

        viewModel.onMessageLiveData.observe(this@ImageProfileEngineerActivity) {
            noticeToast(it)
        }

        viewModel.onFailLiveData.observe(this@ImageProfileEngineerActivity) {
            noticeToast(it)
        }
    }
}