package com.miqdad71.starworks.ui.activity.main.company

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.miqdad71.starworks.R
import com.miqdad71.starworks.data.remote.ApiClient
import com.miqdad71.starworks.databinding.ActivityAddProjectBinding
import com.miqdad71.starworks.ui.activity.signup.SignUpActivity
import com.miqdad71.starworks.util.SharedPreference
import java.text.SimpleDateFormat
import java.util.*

class AddProjectActivity() : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityAddProjectBinding
    private lateinit var preference: SharedPreference
    private lateinit var userDetail: HashMap<String, String>

    private lateinit var viewModel: ProjectViewModel
    private lateinit var myCalendar: Calendar
    private lateinit var deadline: DatePickerDialog.OnDateSetListener

    private var setLayout: Int? = null
    private var bitmap: Bitmap? = null
    private var uri: Uri? = null
    private var pjId: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_project)
        preference = SharedPreference(this)
        userDetail = preference.getAccountUser()
        pjId = intent.getIntExtra("pj_id", 0)

        setToolbarActionBar()
//        initTextWatcher()
        setDataFromIntent()

        myCalendar = Calendar.getInstance()
        deadlineProject()
        setViewModel()
        subscribeLiveData()
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
                addProject()
            }
//          R.id.btn_delete_project -> {
//              deleteConfirmation()
//          }
            R.id.ln_back -> {
                this.finish()
            }
        }
    }

    private fun addProject() {
        val projectName = binding.etProjectName.text.toString()
        val projectDeadline = binding.etDeadline.text.toString()
        val description = binding.etDescription.text.toString()

        preference = SharedPreference(this)

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
        viewModel.serviceCreateApi(
            cnId = preference.getIdCompany(),
            pjProjectName = binding.etProjectName.text.toString(),
            pjDeadline = binding.etDeadline.text.toString(),
            pjDescription = binding.etDescription.text.toString(),
            image = ""

        )
    }

   /* override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                uri = data?.getParcelableExtra("path")!!
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)

                    binding.ibChooseImage.visibility = View.GONE
                    binding.ivImageView.visibility = View.GONE
                    binding.ivImageLoad.visibility = View.VISIBLE
                    binding.ivImageLoad.setImageBitmap(bitmap)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }*/

    private fun setToolbarActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Profile Hire"
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    /*private fun initTextWatcher() {
        binding.etProjectName.addTextChangedListener(MyTextWatcher(binding.etProjectName))
        binding.etDeadline.addTextChangedListener(MyTextWatcher(binding.etDeadline))
        binding.etDescription.addTextChangedListener(MyTextWatcher(binding.etDescription))
    }*/

        private fun setDataFromIntent() {
        if (pjId != 0) {
           /* binding.ibChooseImage.visibility = View.GONE
            binding.ivImageView.visibility = View.VISIBLE
            binding.btnDeleteProject.visibility = View.VISIBLE
            binding.tvAddProject.text = getString(R.string.update_project)
            binding.btnAddProject.text = getString(R.string.update_project)*/

            binding.etProjectName.setText(intent.getStringExtra("pj_project_name"))
            binding.etDeadline.setText(intent.getStringExtra("pj_deadline"))
            binding.etDescription.setText(intent.getStringExtra("pj_description"))

           /* if (intent.getStringExtra("pj_image") != null) {
                binding.imageUrl = BASE_URL_IMAGE + intent.getStringExtra("pj_image")
            } else {
                binding.imageUrl = BASE_URL_IMAGE_DEFAULT_BACKGROUND
            }*/
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
        viewModel.isLoadingLiveData.observe(this) {
            binding.btnAddProject.visibility = View.GONE
        }

        viewModel.onSuccessLiveData.observe(this) {
            if (it) {
                setResult(RESULT_OK)
                this.finish()

                binding.btnAddProject.visibility = View.VISIBLE
            }
        }

        viewModel.onMessageLiveData.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.onFailLiveData.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()

        }
    }

    private fun deleteConfirmation() {
        val dialog = AlertDialog
            .Builder(this)
            .setTitle("Notice!")
            .setMessage("Are you sure to delete this project?")
            .setPositiveButton("OK") { _, _ ->
                viewModel.serviceDeleteApi(pjId = pjId!!)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

        dialog?.show()
    }

    private inline fun <reified ApiService> createApi(context: Context): ApiService {
        return ApiClient.getApiClient(context).create(ApiService::class.java)
    }

}