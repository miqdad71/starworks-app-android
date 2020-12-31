package com.miqdad71.starworks.view.activities.experience

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.ActivityExperienceBinding
import java.text.SimpleDateFormat
import java.util.*

class ExperienceActivity : AppCompatActivity(), View.OnClickListener  {
    private lateinit var myCalendar: Calendar
    private lateinit var date: DatePickerDialog.OnDateSetListener
    private lateinit var binding : ActivityExperienceBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_experience)
        super.onCreate(savedInstanceState)

        setToolbarActionBar()

        myCalendar = Calendar.getInstance()
        date = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val day = findViewById<TextView>(R.id.et_start)
            val myFormat = "dd-mm-yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)

            day.text = sdf.format(myCalendar.time)
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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.et_start -> {
                DatePickerDialog(
                    this@ExperienceActivity, date, myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
            R.id.btn_add_experience -> {

            }
            R.id.ln_back -> {
                this@ExperienceActivity.finish()
            }
        }
    }
}