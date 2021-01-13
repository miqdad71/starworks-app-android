package com.miqdad71.starworks.ui.activity.main.company

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
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

    private var pjId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_project)
        preference = SharedPreference(this)
        userDetail = preference.getAccountUser()
        pjId = intent.getIntExtra("pj_id", 0)

        setToolbarActionBar()
        setDataFromIntent()

        myCalendar = Calendar.getInstance()
        deadlineProject()
        setViewModel()
        subscribeLiveData()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ib_choose_image -> {

            }
            R.id.iv_image_view -> {

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
        viewModel.createAPI(
            cnId = preference.getIdCompany(),
            pjProjectName = binding.etProjectName.text.toString(),
            pjDeadline = binding.etDeadline.text.toString(),
            pjDescription = binding.etDescription.text.toString(),
            image = ""

        )
    }

    private fun setToolbarActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Add Project"
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
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