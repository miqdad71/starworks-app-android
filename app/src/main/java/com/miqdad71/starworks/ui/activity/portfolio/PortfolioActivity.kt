package com.miqdad71.starworks.ui.activity.portfolio

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.miqdad71.starworks.R
import com.miqdad71.starworks.data.remote.ApiClient
import com.miqdad71.starworks.data.remote.ApiClient.Companion.BASE_URL_IMAGE
import com.miqdad71.starworks.databinding.ActivityPortfolioBinding
import com.miqdad71.starworks.ui.base.BaseActivityCoroutine
import com.miqdad71.starworks.ui.fragments.engineer.profile.portfolio.PortfolioViewModel
import com.miqdad71.starworks.util.FileHelper
import com.miqdad71.starworks.util.FileHelper.Companion.createPartFromFile
import com.miqdad71.starworks.util.FileHelper.Companion.createPartFromString
import com.miqdad71.starworks.util.form_validate.ValidatePortfolio.Companion.valAppName
import com.miqdad71.starworks.util.form_validate.ValidatePortfolio.Companion.valDescription
import com.miqdad71.starworks.util.form_validate.ValidatePortfolio.Companion.valLinkPub
import com.miqdad71.starworks.util.form_validate.ValidatePortfolio.Companion.valLinkRepo
import com.miqdad71.starworks.util.form_validate.ValidatePortfolio.Companion.valWorkPlace
import java.util.*

class PortfolioActivity : BaseActivityCoroutine<ActivityPortfolioBinding>(), View.OnClickListener {
    private lateinit var viewModel: PortfolioViewModel
    private var typePortfolio: String? = null
    private var prId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_portfolio
        super.onCreate(savedInstanceState)
        prId = intent.getIntExtra("pr_id", 0)

        setToolbarActionBar()
        initTextWatcher()
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
            R.id.btn_add_portfolio -> {
                when {
                    !valAppName(binding.inputLayoutApp, binding.etApp) -> {}
                    !valDescription(binding.inputLayoutDescription, binding.etDescription) -> {}
                    !valLinkPub(binding.inputLayoutPub, binding.etPubLink) -> {}
                    !valLinkRepo(binding.inputLayoutRepo, binding.etRepoLink) -> {}
                    !valWorkPlace(binding.inputLayoutWorkplace, binding.etWorkPlace) -> {}
                    else -> {
                        when (binding.rgPortfolioType.checkedRadioButtonId) {
                            binding.rbWeb.id -> {
                                typePortfolio = "aplikasi web"
                            }
                            binding.rbMobile.id -> {
                                typePortfolio = "aplikasi mobile"
                            }
                        }

                        if (prId != 0) {
                            if (pathImage == null) {
                                viewModel.serviceUpdateApi(
                                    prId = prId!!,
                                    prApp = createPartFromString(binding.etApp.text.toString()),
                                    prDescription = createPartFromString(binding.etDescription.text.toString()),
                                    prLinkPub = createPartFromString(binding.etPubLink.text.toString()),
                                    prLinkRepo = createPartFromString(binding.etRepoLink.text.toString()),
                                    prWorkPlace = createPartFromString(binding.etWorkPlace.text.toString()),
                                    prType = createPartFromString(typePortfolio!!)
                                )
                            } else {
                                viewModel.serviceUpdateApi(
                                    prId = prId!!,
                                    prApp = createPartFromString(binding.etApp.text.toString()),
                                    prDescription = createPartFromString(binding.etDescription.text.toString()),
                                    prLinkPub = createPartFromString(binding.etPubLink.text.toString()),
                                    prLinkRepo = createPartFromString(binding.etRepoLink.text.toString()),
                                    prWorkPlace = createPartFromString(binding.etWorkPlace.text.toString()),
                                    prType = createPartFromString(typePortfolio!!),
                                    image = createPartFromFile(pathImage!!)
                                )
                            }
                        } else {
                            if (pathImage != null) {
                                viewModel.serviceCreateApi(
                                    enId = createPartFromString(sharedPref.getIdEngineer().toString()),
                                    prApp = createPartFromString(binding.etApp.text.toString()),
                                    prDescription = createPartFromString(binding.etDescription.text.toString()),
                                    prLinkPub = createPartFromString(binding.etPubLink.text.toString()),
                                    prLinkRepo = createPartFromString(binding.etRepoLink.text.toString()),
                                    prWorkPlace = createPartFromString(binding.etWorkPlace.text.toString()),
                                    prType = createPartFromString(typePortfolio!!),
                                    image = createPartFromFile(pathImage!!)
                                )
                            } else {
                                noticeToast("Please select image!")
                            }
                        }
                    }
                }
            }
            R.id.btn_delete_portfolio -> {
                deleteConfirmation()
            }
            R.id.ln_back -> {
                this@PortfolioActivity.finish()
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

            pathImage = FileHelper.getPathFromURI(this@PortfolioActivity, data?.data!!)
        }
    }

    private fun setToolbarActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Portfolio"
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initTextWatcher() {
        binding.etApp.addTextChangedListener(MyTextWatcher(binding.etApp))
        binding.etDescription.addTextChangedListener(MyTextWatcher(binding.etDescription))
        binding.etPubLink.addTextChangedListener(MyTextWatcher(binding.etPubLink))
        binding.etRepoLink.addTextChangedListener(MyTextWatcher(binding.etRepoLink))
        binding.etWorkPlace.addTextChangedListener(MyTextWatcher(binding.etWorkPlace))
    }

    private fun setDataFromIntent() {
        if (prId != 0) {
            binding.ibChooseImage.visibility = View.GONE
            binding.ivImageView.visibility = View.VISIBLE
            binding.btnDeletePortfolio.visibility = View.VISIBLE
            binding.tvAddPortfolio.text = getString(R.string.update_portfolio)
            binding.btnAddPortfolio.text = getString(R.string.update_portfolio)

            binding.etApp.setText(intent.getStringExtra("pr_app"))
            binding.etDescription.setText(intent.getStringExtra("pr_description"))
            binding.etPubLink.setText(intent.getStringExtra("pr_link_pub"))
            binding.etRepoLink.setText(intent.getStringExtra("pr_link_repo"))
            binding.etWorkPlace.setText(intent.getStringExtra("pr_work_place"))

            if (intent.getStringExtra("pr_type") == "aplikasi mobile") {
                binding.rbMobile.isChecked = true
            } else {
                binding.rbWeb.isChecked = true
            }

            if (intent.getStringExtra("pr_image") != null) {
                binding.imageUrl = BASE_URL_IMAGE + intent.getStringExtra("pr_image")
            } else {
                binding.imageUrl = ApiClient.BASE_URL_IMAGE_DEFAULT_BACKGROUND
            }

        }
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this@PortfolioActivity).get(PortfolioViewModel::class.java)
        viewModel.setService(createApi(this@PortfolioActivity))
    }

    private fun subscribeLiveData() {
        viewModel.isLoadingLiveData.observe(this@PortfolioActivity) {
            binding.btnAddPortfolio.visibility = View.GONE
            binding.btnDeletePortfolio.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        }

        viewModel.onSuccessLiveData.observe(this@PortfolioActivity) {
            if (it.isNotEmpty()) {
                noticeToast(it)
                setResult(RESULT_OK)
                this@PortfolioActivity.finish()

                binding.progressBar.visibility = View.GONE
                binding.btnAddPortfolio.visibility = View.VISIBLE
                binding.btnDeletePortfolio.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.btnAddPortfolio.visibility = View.VISIBLE
                binding.btnDeletePortfolio.visibility = View.VISIBLE
            }
        }

        viewModel.onFailLiveData.observe(this@PortfolioActivity) {
            noticeToast(it)
        }
    }

    private fun deleteConfirmation() {
        val dialog = AlertDialog
            .Builder(this@PortfolioActivity)
            .setTitle("Notice!")
            .setMessage("Are you sure to delete this portfolio?")
            .setPositiveButton("OK") { _, _ ->
                viewModel.serviceDeleteApi(prId!!)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

        dialog?.show()
    }

    inner class MyTextWatcher(private val view: View) : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun afterTextChanged(editable: Editable) {
            when (view.id) {
                R.id.et_app -> valAppName(binding.inputLayoutApp, binding.etApp)
                R.id.et_description -> valDescription(binding.inputLayoutDescription, binding.etDescription)
                R.id.et_pub_link -> valLinkPub(binding.inputLayoutPub, binding.etPubLink)
                R.id.et_repo_link -> valLinkRepo(binding.inputLayoutRepo, binding.etRepoLink)
                R.id.et_work_place -> valWorkPlace(binding.inputLayoutWorkplace, binding.etWorkPlace)
            }
        }
    }
}