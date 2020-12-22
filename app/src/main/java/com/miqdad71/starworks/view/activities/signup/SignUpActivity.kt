//package com.miqdad71.starworks.view.activities.signup
//
//import android.os.Bundle
//import android.view.WindowManager
//import androidx.appcompat.app.AppCompatActivity
//import androidx.databinding.DataBindingUtil
//import com.miqdad71.starworks.R
//
//class SignUpActivity : AppCompatActivity() {
//    private lateinit var binding: SignUpActivity
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
//
//        supportActionBar?.hide()
//        window.setFlags(
//                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//        )
//    }
//
////    override fun onClick(v: View?) {
////
////        when (v?.id) {
////            R.id.btn_sign_up -> {
////            }
////            R.id.tv_login -> {
////            }
////        }
////    }
//}