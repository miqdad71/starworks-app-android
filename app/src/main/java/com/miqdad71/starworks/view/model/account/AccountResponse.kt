package com.miqdad71.starworks.view.model.account

import com.google.gson.annotations.SerializedName

data class AccountResponse(val success: Boolean, val message: String, val data: List<AccountItem>){
    data class AccountItem(
        @SerializedName("ac_id")
        val ac_id: Int,
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