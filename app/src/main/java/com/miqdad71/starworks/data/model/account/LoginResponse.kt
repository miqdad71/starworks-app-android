package com.miqdad71.starworks.data.model.account

import com.google.gson.annotations.SerializedName

data class LoginResponse(val success: Boolean, val message: String, val data: LoginItem) {
    data class LoginItem(
        @SerializedName("en_id")
        val enId: Int,

        @SerializedName("cn_id")
        val cnId: Int,

        @SerializedName("ac_id")
        val acId: Int,

        @SerializedName("ac_name")
        val acName: String,

        @SerializedName("ac_email")
        val acEmail: String,

        @SerializedName("en_job_title")
        val enJobTitle: String,

        @SerializedName("en_job_type")
        val enJobType: String,

        @SerializedName("en_domicile")
        val enDomicile: String,

        @SerializedName("en_description")
        val enDescription: String,

        @SerializedName("ac_phone")
        val acPhone: String,

        @SerializedName("ac_password")
        val acPassword: String,

        @SerializedName("ac_level")
        val acLevel: Int,

        @SerializedName("token")
        val token: String
    )
}