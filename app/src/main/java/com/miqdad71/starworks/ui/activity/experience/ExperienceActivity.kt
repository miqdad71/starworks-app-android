package com.miqdad71.starworks.ui.activity.experience

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.miqdad71.starworks.R
import com.miqdad71.starworks.data.remote.ApiClient
import com.miqdad71.starworks.databinding.ActivityExperienceBinding
import com.miqdad71.starworks.ui.activity.signup.SignUpActivity
import com.miqdad71.starworks.util.SharedPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.text.SimpleDateFormat
import java.util.*

class ExperienceActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityExperienceBinding

    private lateinit var sharedPref: SharedPreference
    private lateinit var userDetail: HashMap<String, String>
    private lateinit var viewModel: ExperienceViewModel
    private lateinit var myCalendar: Calendar
    private lateinit var dateStart: DatePickerDialog.OnDateSetListener
    private lateinit var dateEnd: DatePickerDialog.OnDateSetListener
    private lateinit var coroutineScope: CoroutineScope

    private var exId: Int? = null

    companion object {
        const val FIELD_REQUIRED = "Fields cannot be empty"
        const val FIELD_DIGITS_ONLY = "Can only contain numerics"
        const val FIELD_IS_NOT_VALID = "Invalid email"
        const val FIELD_MUST_MATCH = "Password must be the same"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_experience)
        super.onCreate(savedInstanceState)

        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        sharedPref = SharedPreference(this)
        userDetail = sharedPref.getAccountUser()
        exId = intent.getIntExtra("ex_id", 0)

        setToolbarActionBar()
        setDataFromIntent()

        myCalendar = Calendar.getInstance()
        dateStart()
        dateEnd()

        setViewModel()
        subscribeLiveData()

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.et_start -> {
                DatePickerDialog(
                    this@ExperienceActivity, dateStart, myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
            R.id.et_end -> {
                DatePickerDialog(
                    this@ExperienceActivity, dateEnd, myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
            R.id.btn_add_experience -> {
                val position = binding.etPosition.text.toString()
                val company = binding.etCompany.text.toString()
                val dateStart = binding.etStart.text.toString()
                val dateEnd = binding.etEnd.text.toString()
                val description = binding.etDescription.text.toString()

                if (position.isEmpty()) {
                    binding.etPosition.error = FIELD_REQUIRED
                    return
                }
                if (company.isEmpty()) {
                    binding.etCompany.error = FIELD_REQUIRED
                    return
                }
                if (dateStart.isEmpty()) {
                    binding.etStart.error = FIELD_REQUIRED
                    return
                }
                if (dateEnd.isEmpty()) {
                    binding.etEnd.error = FIELD_REQUIRED
                    return
                }
                if (description.isEmpty()) {
                    binding.etDescription.error = FIELD_REQUIRED
                    return
                }

                if (exId != 0) {
                    viewModel.updateAPI(
                        exId = exId!!,
                        exPosition = binding.etPosition.text.toString(),
                        exCompany = binding.etCompany.text.toString(),
                        exStart = binding.etStart.text.toString(),
                        exEnd = binding.etEnd.text.toString(),
                        exDescription = binding.etDescription.text.toString()
                    )
                } else {
                    viewModel.createAPI(
                        enId = sharedPref.getIdEngineer(),
                        exPosition = binding.etPosition.text.toString(),
                        exCompany = binding.etCompany.text.toString(),
                        exStart = binding.etStart.text.toString(),
                        exEnd = binding.etEnd.text.toString(),
                        exDescription = binding.etDescription.text.toString()
                    )
                }
            }
            R.id.btn_delete_experience -> {
                deleteConfirmation()
            }
            R.id.ln_back -> {
                this@ExperienceActivity.finish()
            }
        }
    }

    private fun setToolbarActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Experience"
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setDataFromIntent() {
        if (exId != 0) {
            binding.etPosition.setText(intent.getStringExtra("ex_position"))
            binding.etCompany.setText(intent.getStringExtra("ex_company"))
            binding.etStart.setText(intent.getStringExtra("ex_start"))
            binding.etEnd.setText(intent.getStringExtra("ex_end"))
            binding.etDescription.setText(intent.getStringExtra("ex_description"))

            binding.btnDeleteExperience.visibility = View.VISIBLE
            binding.tvAddExperience.text = getString(R.string.update_experience)
            binding.btnAddExperience.text = getString(R.string.update_experience)
        }
    }

    private fun dateStart() {
        dateStart = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val day = findViewById<TextView>(R.id.et_start)
            val myFormat = "yyyy-MM-dd"
            val sdf = SimpleDateFormat(myFormat, Locale.US)

            day.text = sdf.format(myCalendar.time)
        }
    }

    private fun dateEnd() {
        dateEnd = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val day = findViewById<TextView>(R.id.et_end)
            val myFormat = "yyyy-MM-dd"
            val sdf = SimpleDateFormat(myFormat, Locale.US)

            day.text = sdf.format(myCalendar.time)
        }
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this@ExperienceActivity).get(ExperienceViewModel::class.java)
        viewModel.setService(createApi(this@ExperienceActivity))
    }

    private inline fun <reified ApiService> createApi(context: Context): ApiService {
        return ApiClient.getApiClient(context).create(ApiService::class.java)
    }
    private fun subscribeLiveData() {
        viewModel.isLoadingLiveData.observe(this@ExperienceActivity) {
            binding.btnAddExperience.visibility = View.GONE
            binding.btnDeleteExperience.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        }

        viewModel.onSuccessLiveData.observe(this@ExperienceActivity) {
            if (it) {
                setResult(RESULT_OK)
                this@ExperienceActivity.finish()

                binding.progressBar.visibility = View.GONE
                binding.btnAddExperience.visibility = View.VISIBLE
                binding.btnDeleteExperience.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.btnAddExperience.visibility = View.VISIBLE
                binding.btnDeleteExperience.visibility = View.VISIBLE
            }
        }

        viewModel.onMessageLiveData.observe(this@ExperienceActivity) {
            Toast.makeText(this@ExperienceActivity, "message", Toast.LENGTH_SHORT).show()

        }

        viewModel.onFailLiveData.observe(this@ExperienceActivity) {
            Toast.makeText(this@ExperienceActivity, "message", Toast.LENGTH_SHORT).show()

        }
    }

    private fun deleteConfirmation() {
        val dialog = AlertDialog
            .Builder(this@ExperienceActivity)
            .setTitle("Notice!")
            .setMessage("Are you sure to delete this experience?")
            .setPositiveButton("OK") { _, _ ->
                viewModel.deleteAPI(
                    exId = exId!!
                )
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

        dialog?.show()
    }
}