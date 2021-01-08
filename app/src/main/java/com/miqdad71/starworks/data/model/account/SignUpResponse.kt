package com.miqdad71.starworks.data.model.account

import com.google.gson.annotations.SerializedName

data class SignUpResponse(val success: Boolean, val message: String, val data: RegisterItem) {
    data class RegisterItem(
        @SerializedName("ac_id")
        val acId: Int,

        @SerializedName("ac_name")
        val acName: String,

        @SerializedName("ac_email")
        val acEmail: String,

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