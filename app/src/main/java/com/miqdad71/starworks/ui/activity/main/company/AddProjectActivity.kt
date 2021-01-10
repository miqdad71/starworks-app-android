package com.miqdad71.starworks.ui.activity.main.company

import android.app.DatePickerDialog
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.miqdad71.starworks.R
import com.miqdad71.starworks.api.ProjectApi
import com.miqdad71.starworks.data.model.hire.HireResponse
import com.miqdad71.starworks.data.remote.ApiClient
import com.miqdad71.starworks.databinding.ActivityAddProjectBinding
import com.miqdad71.starworks.ui.activity.signup.SignUpActivity
import com.miqdad71.starworks.util.SharedPreference
import kotlinx.coroutines.*
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.util.*

class AddProjectActivity() : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityAddProjectBinding
    private lateinit var preference: SharedPreference
    private lateinit var userDetail: HashMap<String, String>
    private lateinit var coroutineScope: CoroutineScope
    
    private lateinit var viewModel: ProjectViewModel
    private lateinit var myCalendar: Calendar
    private lateinit var deadline: DatePickerDialog.OnDateSetListener
    
    private var setLayout: Int? = null
    private var bitmap: Bitmap? = null
    private var uri: Uri? = null
    private var pjId: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_project)
        preference = SharedPreference(this)
        userDetail = preference.getAccountUser()
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        super.onCreate(savedInstanceState)
        pjId = intent.getIntExtra("pj_id", 0)

        setToolbarActionBar()
//        initTextWatcher()
//        setDataFromIntent()

        myCalendar = Calendar.getInstance()
//        deadlineProject()
//
//        setViewModel()
//        subscribeLiveData()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ib_choose_image -> {
//                selectImage()
            }
            R.id.iv_image_view -> {
//                selectImage()
            }
            R.id.et_deadline -> {
                DatePickerDialog(
                    this, deadline, myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
            R.id.btn_add_project -> {
                addProject(v)
            }
//          when {
//              !valProjectName(binding.inputLayoutProjectName, binding.etProjectName) -> {
//              }
//              !valDeadline(binding.inputLayoutDeadline, binding.etDeadline) -> {
//              }
//              !valDescription(binding.inputLayoutDescription, binding.etDescription) -> {
//              }
//              else -> {
//                  if (pjId != 0) {
//                      if (bitmap == null) {
//                          viewModel.serviceUpdateApi(
//                              pjId = pjId!!,
//                              pjProjectName = createPartFromString(binding.etProjectName.text.toString()),
//                              pjDeadline = createPartFromString(binding.etDeadline.text.toString()),
//                              pjDescription = createPartFromString(binding.etDescription.text.toString())
//                          )
//                      } else {
//                          viewModel.serviceUpdateApi(
//                              pjId = pjId!!,
//                              pjProjectName = createPartFromString(binding.etProjectName.text.toString()),
//                              pjDeadline = createPartFromString(binding.etDeadline.text.toString()),
//                              pjDescription = createPartFromString(binding.etDescription.text.toString()),
//                              image = createPartFromFile()
//                          )
//                      }
//                  } else {
//                      if (bitmap != null) {
//                          viewModel.serviceCreateApi(
//                              cnId = createPartFromString(preference.getIdCompany().toString()),
//                              pjProjectName = createPartFromString(binding.etProjectName.text.toString()),
//                              pjDeadline = createPartFromString(binding.etDeadline.text.toString()),
//                              pjDescription = createPartFromString(binding.etDescription.text.toString()),
//                              image = createPartFromFile()
//                          )
//                      } else {
//                          noticeToast("Please select image!")
//                      }
//                  }
//              }
//          }

//          R.id.btn_delete_project -> {
//              deleteConfirmation()
//          }
            R.id.ln_back -> {
                this.finish()
            }
        }
    }


    private fun addProject(view: View) {
        val projectName = binding.etProjectName.text.toString()
        val projectDeadline = binding.etDeadline.text.toString()
        val description = binding.etDescription.text.toString()

        preference = SharedPreference(view.context)

            if (projectName.isEmpty()) {
                binding.etProjectName.error = SignUpActivity.FIELD_REQUIRED
                return
            }
            if (projectDeadline.isEmpty()) {
                binding.etDeadline.error = SignUpActivity.FIELD_IS_NOT_VALID
                return
            }
            if (description.isEmpty()) {
                binding.etDescription.error = SignUpActivity.FIELD_REQUIRED
                return
            }

        addProjectId()
    }
    fun addProjectId() {
        val api = ApiClient.getApiClient(this).create(ProjectApi::class.java)
        coroutineScope.launch {
            val res = withContext(Dispatchers.IO) {
                try {
                    api.createProject(
                        cnId = preference.getIdCompany().toString().toRequestBody(),
                        pjProjectName = binding.etProjectName.text.toString().toRequestBody(),
                        pjDeadline = binding.etDeadline.text.toString().toRequestBody(),
                        pjDescription = binding.etDescription.text.toString().toRequestBody()
                    )
                } catch (e: HttpException) {
                    runOnUiThread {
                        if (e.code() == 400) {
                            Toast.makeText(this@AddProjectActivity, "Project Successfully Added", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@AddProjectActivity, "Added Project Failed, Please try again later!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }


            if (res is HireResponse) {
                if (res.success) {
                    Toast.makeText(this@AddProjectActivity, res.message, Toast.LENGTH_SHORT).show()
                    this@AddProjectActivity.finish()
                } else {
                    Toast.makeText(this@AddProjectActivity, res.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == REQUEST_IMAGE) {
//            if (resultCode == RESULT_OK) {
//                uri = data?.getParcelableExtra("path")!!
//                try {
//                    bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
//
//                    binding.ibChooseImage.visibility = View.GONE
//                    binding.ivImageView.visibility = View.GONE
//                    binding.ivImageLoad.visibility = View.VISIBLE
//                    binding.ivImageLoad.setImageBitmap(bitmap)
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
//            }
//        }
//    }


    private fun setToolbarActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Profile Hire"
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

//    private fun initTextWatcher() {
//        binding.etProjectName.addTextChangedListener(MyTextWatcher(binding.etProjectName))
//        binding.etDeadline.addTextChangedListener(MyTextWatcher(binding.etDeadline))
//        binding.etDescription.addTextChangedListener(MyTextWatcher(binding.etDescription))
//    }

//    private fun setDataFromIntent() {
//        if (pjId != 0) {
//            binding.ibChooseImage.visibility = View.GONE
//            binding.ivImageView.visibility = View.VISIBLE
//            binding.btnDeleteProject.visibility = View.VISIBLE
//            binding.tvAddProject.text = getString(R.string.update_project)
//            binding.btnAddProject.text = getString(R.string.update_project)
//
//            binding.etProjectName.setText(intent.getStringExtra("pj_project_name"))
//            binding.etDeadline.setText(intent.getStringExtra("pj_deadline"))
//            binding.etDescription.setText(intent.getStringExtra("pj_description"))
//
//            if (intent.getStringExtra("pj_image") != null) {
//                binding.imageUrl = BASE_URL_IMAGE + intent.getStringExtra("pj_image")
//            } else {
//                binding.imageUrl = BASE_URL_IMAGE_DEFAULT_BACKGROUND
//            }
//        }
//    }
//
//    private fun deadlineProject() {
//        deadline = OnDateSetListener { _, year, month, dayOfMonth ->
//            myCalendar.set(Calendar.YEAR, year)
//            myCalendar.set(Calendar.MONTH, month)
//            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
//
//            val day = findViewById<TextView>(R.id.et_deadline)
//            val myFormat = "yyyy-MM-dd"
//            val sdf = SimpleDateFormat(myFormat, Locale.US)
//
//            day.text = sdf.format(myCalendar.time)
//        }
//    }
//
//    private fun setViewModel() {
//        viewModel = ViewModelProvider(this@ProjectActivity).get(ProjectViewModel::class.java)
//        viewModel.setService(createApi(this@ProjectActivity))
//    }
//
//    private fun subscribeLiveData() {
//        viewModel.isLoadingLiveData.observe(this@ProjectActivity, {
//            binding.btnAddProject.visibility = View.GONE
//            binding.btnDeleteProject.visibility = View.GONE
//            binding.progressBar.visibility = View.VISIBLE
//        })
//
//        viewModel.onSuccessLiveData.observe(this@ProjectActivity, {
//            if (it) {
//                setResult(RESULT_OK)
//                this@ProjectActivity.finish()
//
//                binding.progressBar.visibility = View.GONE
//                binding.btnAddProject.visibility = View.VISIBLE
//                binding.btnDeleteProject.visibility = View.VISIBLE
//            } else {
//                binding.progressBar.visibility = View.GONE
//                binding.btnAddProject.visibility = View.VISIBLE
//                binding.btnDeleteProject.visibility = View.VISIBLE
//            }
//        })
//
//        viewModel.onMessageLiveData.observe(this@ProjectActivity, {
//            noticeToast(it)
//        })
//
//        viewModel.onFailLiveData.observe(this@ProjectActivity, {
//            noticeToast(it)
//        })
//    }
//
//    private fun deleteConfirmation() {
//        val dialog = AlertDialog
//            .Builder(this@ProjectActivity)
//            .setTitle("Notice!")
//            .setMessage("Are you sure to delete this project?")
//            .setPositiveButton("OK") { _, _ ->
//                viewModel.serviceDeleteApi(pjId = pjId!!)
//            }
//            .setNegativeButton("Cancel") { dialog, _ ->
//                dialog.dismiss()
//            }
//
//        dialog?.show()
//    }
//
//    inner class MyTextWatcher(private val view: View) : TextWatcher {
//        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
//        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
//        override fun afterTextChanged(editable: Editable) {
//            when (view.id) {
//                R.id.et_project_name -> valProjectName(
//                    binding.inputLayoutProjectName,
//                    binding.etProjectName
//                )
//                R.id.et_deadline -> valDeadline(binding.inputLayoutDeadline, binding.etDeadline)
//                R.id.et_description -> valDescription(
//                    binding.inputLayoutDescription,
//                    binding.etDescription
//                )
//            }
//        }
//    }

}