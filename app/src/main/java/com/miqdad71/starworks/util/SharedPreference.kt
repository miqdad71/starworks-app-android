package com.miqdad71.starworks.util

import android.content.Context
import android.content.SharedPreferences
import com.miqdad71.starworks.data.model.engineer.EngineerModel

class SharedPreference(context: Context) {
    companion object {
        const val TOKEN = "token"
        const val ENG_PREF_NAME = "eng_pref"
        const val COMP_PREF_NAME = "comp_pref"
        const val EMAIL = "email"
        const val NAME = "name"
        const val AC_ID = "ac_id"
        const val AC_LEVEL = "ac_level"
        const val PASSWORD = "password"
        const val PHONE = "phone"
        const val COMPANY = "company"
        const val POSITION = "position"
        const val LOGIN = "isLogin"
        const val AC_DETAIL = "AC_DETAIL"
    }

    private val engineerPreferences =
        context.getSharedPreferences(ENG_PREF_NAME, Context.MODE_PRIVATE)
    private val companyPreferences =
        context.getSharedPreferences(COMP_PREF_NAME, Context.MODE_PRIVATE)
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(ENG_PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

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

    fun createInDetail(acDetail: Int) {
        editor.putInt(AC_DETAIL, acDetail)
        editor.commit()
    }

    fun getAccountUser(): HashMap<String, String> {
        val user: HashMap<String, String> = HashMap()
        user[NAME] = sharedPreferences.getString(NAME, "Not set")!!
        user[EMAIL] = sharedPreferences.getString(EMAIL, "Not set")!!
        user[TOKEN] = sharedPreferences.getString(TOKEN, "Not set")!!

        return user
    }

    fun setAccount(ac_name: String, ac_id: Int, ac_level: Int, ac_email: String) {
        val editor = engineerPreferences.edit()
        editor.putString(NAME, ac_name)
        editor.putString(EMAIL, ac_email)
        editor.putInt(AC_ID, ac_id)
        editor.putInt(AC_LEVEL, ac_level)
        editor.putBoolean(LOGIN, true)
        editor.apply()
    }

//    fun setEngineerPreference(value: EngineerModel) {
//        val editor = engineerPreferences.edit()
//        editor.putString(EMAIL, value.email)
//        editor.putString(PASSWORD, value.password)
//        editor.putLong(PHONE, value.phone)
//        editor.putBoolean(LOGIN, value.isLogin)
//        editor.apply()
//    }

}