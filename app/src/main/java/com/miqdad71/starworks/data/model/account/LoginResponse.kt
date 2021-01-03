package com.miqdad71.starworks.data.model.account

import com.google.gson.annotations.SerializedName

data class LoginResponse(val success: Boolean, val message: String, val data: LoginItem) {
    data class LoginItem(
        @SerializedName("ac_id")
        val ac_id: Int,
        @SerializedName("en_id")
        val en_id: Int? = null,
        @SerializedName("cn_id")
        val cn_id: Int? = null,
        @SerializedName("ac_name")
        val ac_name: String,
        @SerializedName("ac_email")
        val ac_email: String,
        @SerializedName("ac_phone")
        val ac_phone: String,
        @SerializedName("ac_password")
        val ac_password: String,
        @SerializedName("ac_level")
        val ac_level: Int,
        @SerializedName("token")
        val token: String
    )
}