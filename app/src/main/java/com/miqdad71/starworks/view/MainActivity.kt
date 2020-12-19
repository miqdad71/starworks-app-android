package com.miqdad71.starworks.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.miqdad71.starworks.R

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val btn_login = findViewById<Button>(R.id.btn_login)
        val btn_register = findViewById<Button>(R.id.btn_register)

        btn_login.setOnClickListener(this)
        btn_register.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> {
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("level", 0)
                startActivity(intent)
            }
            R.id.btn_register -> {
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("level", 1)
                startActivity(intent)
            }

        }
    }

}