package com.miqdad71.starworks.view.dialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.miqdad71.starworks.R
import com.miqdad71.starworks.databinding.ActivityDialogBinding

class DialogActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dialog)

        binding.btn1.setOnClickListener {
            showDialogOne()
        }
        binding.btn2.setOnClickListener {
            showDialogTwo()
        }
        binding.btn3.setOnClickListener {
            showDialogThree()
        }

    }

    private fun  showDialogOne(){
        val dialog = AlertDialog.Builder(this)
            .setTitle("Test Dialog One")
            .setMessage("Ini Dialog 1")
            .create()
        dialog.show()
    }
    private fun  showDialogTwo(){
        val dialog = AlertDialog.Builder(this)
            .setTitle("Test Dialog Two")
            .setMessage("Ini Dialog 2")
            .create()
        dialog.show()
    }
    private fun  showDialogThree(){
        val dialog = AlertDialog.Builder(this)
            .setTitle("Test Dialog Three")
            .setMessage("Ini Dialog 3")
            .create()
        dialog.show()
    }


}