package com.miqdad71.starworks.ui.activity.main.engineer.img_profile

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.miqdad71.starworks.R
import com.miqdad71.starworks.data.remote.ApiClient
import com.miqdad71.starworks.databinding.ActivityImageProfileEngineerBinding
import com.miqdad71.starworks.util.FileHelper
import com.miqdad71.starworks.util.FileHelper.Companion.createPartFromFile

open class ImageProfileEngineer : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityImageProfileEngineerBinding

    private lateinit var viewModel: ImageProfileEngineerViewModel
    private var enId: Int? = null
    private var enProfile: String? = null
    private var pathImage: String? = null

    companion object {
        const val IMAGE_PICK_CODE = 100
        const val PERMISSION_CODE = 200
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_profile_engineer)
        super.onCreate(savedInstanceState)

        enId = intent.getIntExtra("en_id", 0)
        enProfile = intent.getStringExtra("en_profile")

        setToolbarActionBar()
        setDataFromIntent()
        setViewModel()
        subscribeLiveData()
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
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
                        this@ImageProfileEngineer.finish()
                    } else {
                        viewModel.serviceUpdateImageEngineer(
                            enId = enId!!,
                            image = createPartFromFile(pathImage!!)
                        )
                    }
                } else {
                    Toast.makeText(this@ImageProfileEngineer, "Please login again!", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.ln_back -> {
                this@ImageProfileEngineer.finish()
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
                    Toast.makeText(this@ImageProfileEngineer, "Permission denied...!", Toast.LENGTH_SHORT).show()
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

            pathImage = FileHelper.getPathFromURI(this@ImageProfileEngineer, data?.data!!)
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
            }

        }
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this@ImageProfileEngineer).get(ImageProfileEngineerViewModel::class.java)
        viewModel.setService(createApi(this@ImageProfileEngineer))
    }
    private inline fun <reified ApiService> createApi(context: Context): ApiService {
        return ApiClient.getApiClient(context).create(ApiService::class.java)
    }

    private fun subscribeLiveData() {
        viewModel.isLoadingLiveData.observe(this@ImageProfileEngineer) {
            binding.btnUpdateImage.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        }

        viewModel.onSuccessLiveData.observe(this@ImageProfileEngineer) {
            if (it) {
                setResult(RESULT_OK)
                this@ImageProfileEngineer.finish()

                binding.progressBar.visibility = View.GONE
                binding.btnUpdateImage.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.btnUpdateImage.visibility = View.VISIBLE
            }
        }

         viewModel.onMessageLiveData.observe(this@ImageProfileEngineer) {
             Toast.makeText(this@ImageProfileEngineer, "Upload Success", Toast.LENGTH_SHORT)
                 .show()

         }

        viewModel.onFailLiveData.observe(this@ImageProfileEngineer) {
            Toast.makeText(this@ImageProfileEngineer, "Upload Failed", Toast.LENGTH_SHORT)
                .show()
        }
    }
}