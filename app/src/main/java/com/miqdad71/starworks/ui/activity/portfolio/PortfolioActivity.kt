package com.miqdad71.starworks.ui.activity.portfolio

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.miqdad71.starworks.R
import com.miqdad71.starworks.data.remote.ApiClient
import com.miqdad71.starworks.data.remote.ApiClient.Companion.BASE_URL_IMAGE
import com.miqdad71.starworks.databinding.ActivityPortfolioBinding
import com.miqdad71.starworks.ui.activity.signup.SignUpActivity
import com.miqdad71.starworks.util.SharedPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.util.HashMap

class PortfolioActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityPortfolioBinding

    private lateinit var preference: SharedPreference
    private lateinit var userDetail: HashMap<String, String>
    private lateinit var viewModel: PortfolioViewModel
    private lateinit var coroutineScope: CoroutineScope

    private var prId: Int? = null
    private var pathImage: String? = null
    private var typePortfolio: String? = null

    companion object {
        private const val IMAGE_PICK_CODE = 1000;
        private const val PERMISSION_CODE = 1001;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_portfolio)
        super.onCreate(savedInstanceState)

        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        preference = SharedPreference(this)
        userDetail = preference.getAccountUser()
        prId = intent.getIntExtra("pj_id", 0)

        binding.ibChooseImage.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    requestPermissions(permissions, PERMISSION_CODE);
                } else {
                    pickImageFromGallery();
                }
            } else {
                pickImageFromGallery();
            }
        }

        setToolbarActionBar()
        setDataFromIntent()
        setViewModel()
        subscribeLiveData()
    }

    private fun setToolbarActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Portfolio"
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_add_portfolio -> {
                val appName = binding.etApp.text.toString()
                val descPort = binding.etDescription.text.toString()
                val pubLink = binding.etPubLink.text.toString()
                val repLink = binding.etRepoLink.text.toString()
                val workPlace = binding.etWorkPlace.text.toString()

                if (appName.isEmpty()) {
                    binding.etApp.error = SignUpActivity.FIELD_REQUIRED
                    return
                }
                if (descPort.isEmpty()) {
                    binding.etDescription.error = SignUpActivity.FIELD_REQUIRED
                    return
                }
                if (pubLink.isEmpty()) {
                    binding.etPubLink.error = SignUpActivity.FIELD_REQUIRED
                    return
                }
                if (repLink.isEmpty()) {
                    binding.etRepoLink.error = SignUpActivity.FIELD_REQUIRED
                    return
                }
                if (workPlace.isEmpty()) {
                    binding.etWorkPlace.error = SignUpActivity.FIELD_REQUIRED
                    return
                }

                when (binding.rgPortfolioType.checkedRadioButtonId) {
                    binding.rbWeb.id -> {
                        typePortfolio = "aplikasi web"
                    }
                    binding.rbMobile.id -> {
                        typePortfolio = "aplikasi mobile"
                    }
                }
                
                if (prId != 0){
                    if (pathImage == null) {
                        viewModel.updateAPI(
                            prId = prId!!,
                            prApp = createPartFromString(binding.etApp.text.toString()),
                            prDescription = createPartFromString(binding.etDescription.text.toString()),
                            prLinkPub = createPartFromString(binding.etPubLink.text.toString()),
                            prLinkRepo = createPartFromString(binding.etRepoLink.text.toString()),
                            prWorkPlace = createPartFromString(binding.etWorkPlace.text.toString()),
                            prType = createPartFromString(typePortfolio!!)
                        )
                    } else {
                        viewModel.updateAPI(
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
                        viewModel.createAPI(
                            enId = createPartFromString(preference.getIdEngineer().toString()),
                            prApp = createPartFromString(binding.etApp.text.toString()),
                            prDescription = createPartFromString(binding.etDescription.text.toString()),
                            prLinkPub = createPartFromString(binding.etPubLink.text.toString()),
                            prLinkRepo = createPartFromString(binding.etRepoLink.text.toString()),
                            prWorkPlace = createPartFromString(binding.etWorkPlace.text.toString()),
                            prType = createPartFromString(typePortfolio!!),

                            image = createPartFromFile(pathImage!!)
                        )
                    } else {
                        Toast.makeText(this, "Please select image!", Toast.LENGTH_SHORT).show()
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

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            binding.ibChooseImage.setImageURI(data?.data)
            pathImage = getPath(this, data?.data!!)
        }
    }
    private fun createPartFromString(descriptionString: String): RequestBody {
        return descriptionString.toRequestBody(MultipartBody.FORM)
    }

    private fun createPartFromFile(path: String): MultipartBody.Part {
        val file = File(path)
        val reqFile: RequestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("image", file.name, reqFile)
    }

    private fun getPath(context: Context, uri: Uri): String? {
        var realPath = String()
        uri.path?.let { path ->

            val databaseUri: Uri
            val selection: String?
            val selectionArgs: Array<String>?
            if (path.contains("/document/image:")) {
                databaseUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                selection = "_id=?"
                selectionArgs = arrayOf(DocumentsContract.getDocumentId(uri).split(":")[1])
            } else {
                databaseUri = uri
                selection = null
                selectionArgs = null
            }

            try {
                val column = "_data"
                val projection = arrayOf(column)
                val cursor = context.contentResolver.query(
                    databaseUri,
                    projection,
                    selection,
                    selectionArgs,
                    null
                )
                cursor?.let {
                    if (it.moveToFirst()) {
                        val columnIndex = cursor.getColumnIndexOrThrow(column)
                        realPath = cursor.getString(columnIndex)
                    }
                    cursor.close()
                }
            } catch (e: Exception) {
                println(e)
            }
        }
        return realPath
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
            }
//            else {
//                binding.imageUrl = ApiClient.BASE_URL_IMAGE_DEFAULT_BACKGROUND
//            }

        }
    }
    private fun deleteConfirmation() {
        val dialog = AlertDialog
            .Builder(this@PortfolioActivity)
            .setTitle("Notice!")
            .setMessage("Are you sure to delete this portfolio?")
            .setPositiveButton("OK") { _, _ ->
                viewModel.deleteAPI(prId!!)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

        dialog?.show()
    }
    
    private fun setViewModel() {
        viewModel = ViewModelProvider(this).get(PortfolioViewModel::class.java)
        viewModel.setService(createApi(this))
    }
    private fun subscribeLiveData() {
        viewModel.onSuccessLiveData.observe(this) {
            if (it) {
                setResult(RESULT_OK)
                this.finish()
            }
        }

    }

    private inline fun <reified ApiService> createApi(context: Context): ApiService {
        return ApiClient.getApiClient(context).create(ApiService::class.java)
    }

}