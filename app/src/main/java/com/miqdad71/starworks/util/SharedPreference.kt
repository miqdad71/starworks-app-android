package com.miqdad71.starworks.util

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.miqdad71.starworks.ui.activity.OnboardActivity
import com.miqdad71.starworks.ui.activity.main.company.CompanyMainActivity
import com.miqdad71.starworks.ui.activity.main.engineer.EngineerMainActivity

class SharedPreference(private val context: Context) {
    companion object {

        const val TOKEN = "token"
        const val PREF_NAME = "PREF_NAME"
        const val AC_EMAIL = "AC_EMAIL"
        const val AC_NAME = "AC_NAME"
        const val AC_PHONE = "AC_PHONE"
        const val EXPIRED = "EXPIRED"

        const val AC_ID = "AC_ID"
        const val EN_ID = "EN_ID"
        const val CN_ID = "CN_ID"

        const val AC_LEVEL = "AC_LEVEL"
        const val LOGIN = "isLogin"
        const val AC_DETAIL = "AC_DETAIL"

    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun createInDetail(acDetail: Int) {
        editor.putInt(AC_DETAIL, acDetail)
        editor.commit()
    }


    fun createAccountUser(
        id: Int,
        acId: Int,
        acLevel: Int,
        acName: String,
        acEmail: String,
        token: String,
        exp: Long
    ) {
        editor.putBoolean(LOGIN, true)
        editor.putInt(AC_LEVEL, acLevel)
        editor.putString(AC_NAME, acName)
        editor.putString(AC_EMAIL, acEmail)
        editor.putString(TOKEN, token)
        editor.putLong(EXPIRED, exp)
        editor.putInt(AC_ID, acId)

        if (acLevel == 0) {
            editor.putInt(EN_ID, id)
        } else {
            editor.putInt(CN_ID, id)
        }

        editor.commit()
    }

    fun getAccountUser(): HashMap<String, String> {
        val user: HashMap<String, String> = HashMap()
        user[AC_NAME] = sharedPreferences.getString(AC_NAME, "Not set")!!
        user[AC_EMAIL] = sharedPreferences.getString(AC_EMAIL, "Not set")!!
        user[AC_PHONE] = sharedPreferences.getString(AC_PHONE, "Not set")!!
        user[TOKEN] = sharedPreferences.getString(TOKEN, "Not set")!!

        return user
    }

    fun setToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString(TOKEN, token)
        editor.apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(TOKEN, "Not Set")
    }

    fun getLevelUser(): Int {
        return sharedPreferences.getInt(AC_LEVEL, 0)
    }


    fun getInDetail(): Int {
        return sharedPreferences.getInt(AC_DETAIL, 0)
    }

    fun getIsLogin(): Boolean {
        return sharedPreferences.getBoolean(LOGIN, false)
    }

    fun getIdAccount(): Int {
        return sharedPreferences.getInt(AC_ID, 0)
    }

    fun getIdEngineer(): Int {
        return sharedPreferences.getInt(EN_ID, 0)
    }

    fun getIdCompany(): Int {
        return sharedPreferences.getInt(CN_ID, 0)
    }

    fun setAccountEngineer(acName: String, acId: Int, enId: Int, acLevel: Int, acEmail: String, acPhone: String) {
        val editor = sharedPreferences.edit()
        editor.putString(AC_NAME, acName)
        editor.putInt(AC_ID, acId)
        editor.putInt(EN_ID, enId)
        editor.putInt(AC_LEVEL, acLevel)
        editor.putString(AC_EMAIL, acEmail)
        editor.putString(AC_PHONE, acPhone)
        editor.putBoolean(LOGIN, true)
        editor.apply()
    }

    fun setAccountCompany(acName: String, acId: Int, cnId: Int, acLevel: Int, acEmail: String) {
        val editor = sharedPreferences.edit()
        editor.putString(AC_NAME, acName)
        editor.putString(AC_EMAIL, acEmail)
        editor.putInt(AC_ID, acId)
        editor.putInt(CN_ID, cnId)
        editor.putInt(AC_LEVEL, acLevel)
        editor.putBoolean(LOGIN, true)
        editor.apply()
    }

    fun accountLogout() {
        editor.clear()
        editor.commit()

        context.startActivity(Intent(context, OnboardActivity::class.java))
        if (getLevelUser() == 0){
            (context as EngineerMainActivity).finish()
        } else {
            (context as CompanyMainActivity).finish()
        }
    }
}