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
import android.provider.MediaStore
import android.view.View
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.loader.content.CursorLoader
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.ActivityAddProjectBinding
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

class AddProjectActivity() : BaseActivityCoroutine<ActivityAddProjectBinding>(), View.OnClickListener {

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
        setLayout = R.layout.activity_add_project
        super.onCreate(savedInstanceState)

        sharedPref = SharedPreference(this)
        userDetail = sharedPref.getAccountUser()
        pjId = intent.getIntExtra("pj_id", 0)

        setToolbarActionBar()
        setDataFromIntent()

        myCalendar = Calendar.getInstance()
        deadlineProject()
        setViewModel()
        subscribeLiveData()

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


    }

    private fun setToolbarActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Add Project"
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
            R.id.btn_add_project -> {
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
                    viewModel.createAPI(
                        cnId = createPartFromString(sharedPref.getIdCompany().toString()),
                        pjProjectName = createPartFromString(binding.etProjectName.text.toString()),
                        pjDeadline = createPartFromString(binding.etDeadline.text.toString()),
                        pjDescription = createPartFromString(binding.etDescription.text.toString()),
                        image = createPartFromFile(pathImage!!)
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

    private fun getPath(context: Context, contentUri: Uri): String? {
        var result: String? = null
        val imageProfile = arrayOf(MediaStore.Images.Media.DATA)

        val cursorLoader = CursorLoader(context, contentUri, imageProfile, null, null, null)
        val cursor = cursorLoader.loadInBackground()

        if (cursor != null) {
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            result = cursor.getString(columnIndex)
            cursor.close()
        }

        return result
    }


    private fun setDataFromIntent() {
        if (pjId != 0) {
            binding.etProjectName.setText(intent.getStringExtra("pj_project_name"))
            binding.etDeadline.setText(intent.getStringExtra("pj_deadline"))
            binding.etDescription.setText(intent.getStringExtra("pj_description"))
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
        
        viewModel.isLoadingLiveData.observe(this@AddProjectActivity) {
            binding.btnAddProject.visibility = View.GONE
            binding.btnDeleteProject.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        }

        viewModel.onSuccessLiveData.observe(this@AddProjectActivity) {
            if (it.isNotEmpty()) {
                setResult(RESULT_OK)
                this@AddProjectActivity.finish()

                binding.progressBar.visibility = View.GONE
                binding.btnAddProject.visibility = View.VISIBLE
                binding.btnDeleteProject.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.btnAddProject.visibility = View.VISIBLE
                binding.btnDeleteProject.visibility = View.VISIBLE
            }
        }

        viewModel.onFailLiveData.observe(this@AddProjectActivity) {
            noticeToast(it)
        }
        
    }

}