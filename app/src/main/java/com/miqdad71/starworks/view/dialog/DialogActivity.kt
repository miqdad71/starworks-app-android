package com.miqdad71.starworks.view.dialog

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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

    private fun showDialogOne() {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Test Dialog One")
            .setMessage("Ini Dialog 1")
            .create()
        dialog.show()
    }

    private fun showDialogTwo() {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Konfirmasi User")
            .setMessage("Tolong Konfirmasi")
            .setPositiveButton("Show Toast") { dialog: DialogInterface?, which: Int ->
                Toast.makeText(this, "Show Dialog 2", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Dissmiss") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
        dialog.show()
    }

    private fun showDialogThree() {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Test Dialog Three")
            .setMessage("Ini Dialog 3")
            .create()
        dialog.show()
    }


}