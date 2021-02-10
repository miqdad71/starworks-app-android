package com.miqdad71.starworks.ui.activity.main.company

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.miqdad71.starworks.R
import com.miqdad71.starworks.data.remote.ApiClient
import com.miqdad71.starworks.databinding.ActivityAddProjectBinding
import com.miqdad71.starworks.databinding.ActivityEditProjectBinding
import com.miqdad71.starworks.ui.activity.signup.SignUpActivity
import com.miqdad71.starworks.ui.base.BaseActivityCoroutine
import com.miqdad71.starworks.util.SharedPreference
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class EditProjectActivity() : BaseActivityCoroutine<ActivityEditProjectBinding>(), View.OnClickListener {

    private lateinit var viewModel: ProjectViewModel
    private lateinit var myCalendar: Calendar
    private lateinit var deadline: DatePickerDialog.OnDateSetListener

    private var pjId: Int? = null

    companion object {
        const val FIELD_REQUIRED = "Fields cannot be empty"
        const val FIELD_IS_NOT_VALID = "Invalid email"
    }

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_edit_project
        super.onCreate(savedInstanceState)

        sharedPref = SharedPreference(this)
        userDetail = sharedPref.getAccountUser()
        pjId = intent.getIntExtra("pj_id", 0)

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

        binding.ivImageLoad.setOnClickListener {
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

        myCalendar = Calendar.getInstance()
        deadlineProject()
        setViewModel()
        subscribeLiveData()
    }

    private fun setToolbarActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Edit Project"
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.et_deadline -> {
                DatePickerDialog(
                    this, deadline, myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
            R.id.btn_edit_project -> {
                val projectName = binding.etProjectName.text.toString()
                val projectDeadline = binding.etDeadline.text.toString()
                val description = binding.etDescription.text.toString()

                if (projectName.isEmpty()) {
                    binding.etProjectName.error = FIELD_REQUIRED
                    return
                }
                if (projectDeadline.isEmpty()) {
                    binding.etDeadline.error = FIELD_IS_NOT_VALID
                    return
                }
                if (description.isEmpty()) {
                    binding.etDescription.error = FIELD_REQUIRED
                    return
                }
                if (pathImage != null) {
                    viewModel.updateAPI(
                        pjId = pjId!!,
                        pjProjectName = createPartFromString(binding.etProjectName.text.toString()),
                        pjDeadline = createPartFromString(binding.etDeadline.text.toString()),
                        pjDescription = createPartFromString(binding.etDescription.text.toString()),
                        image = createPartFromFile(pathImage!!)
                    )
                } else {
                    viewModel.updateAPI(
                        pjId = pjId!!,
                        pjProjectName = createPartFromString(binding.etProjectName.text.toString()),
                        pjDeadline = createPartFromString(binding.etDeadline.text.toString()),
                        pjDescription = createPartFromString(binding.etDescription.text.toString())
                    )
                }
            }
            R.id.ln_back -> {
                this.finish()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            binding.ibChooseImage.setImageURI(data?.data)
            binding.ibChooseImage.visibility = View.VISIBLE
            binding.ivImageLoad.visibility = View.GONE
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
        if (pjId != 0) {
            binding.etProjectName.setText(intent.getStringExtra("pj_project_name"))
            binding.etDeadline.setText(intent.getStringExtra("pj_deadline"))
            binding.etDescription.setText(intent.getStringExtra("pj_description"))
            Glide.with(this@EditProjectActivity)
                .load(ApiClient.BASE_URL_IMAGE + intent.getStringExtra("pj_image"))
                .placeholder(R.drawable.ic_backround_user).into(binding.ivImageLoad)
            binding.ibChooseImage.visibility = View.GONE
            binding.ivImageLoad.visibility = View.VISIBLE
        }
    }

    private fun deadlineProject() {
        deadline = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val day = findViewById<TextView>(R.id.et_deadline)
            val myFormat = "yyyy-MM-dd"
            val sdf = SimpleDateFormat(myFormat, Locale.US)

            day.text = sdf.format(myCalendar.time)
        }
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this).get(ProjectViewModel::class.java)
        viewModel.setService(createApi(this))
    }

    private fun subscribeLiveData() {

        viewModel.isLoadingLiveData.observe(this@EditProjectActivity) {
            binding.btnEditProject.visibility = View.GONE
            binding.btnDeleteProject.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        }

        viewModel.onSuccessLiveData.observe(this@EditProjectActivity) {
            if (it.isNotEmpty()) {
                setResult(RESULT_OK)
                this@EditProjectActivity.finish()

                binding.progressBar.visibility = View.GONE
                binding.btnEditProject.visibility = View.VISIBLE
                binding.btnDeleteProject.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.btnEditProject.visibility = View.VISIBLE
                binding.btnDeleteProject.visibility = View.VISIBLE
            }
        }

        viewModel.onFailLiveData.observe(this@EditProjectActivity) {
            noticeToast(it)
        }

    }
}