package com.miqdad71.starworks.data.model.hire

import com.google.gson.annotations.SerializedName

data class HireResponse(val success: Boolean, val message: String, val data: LoginItem) {
    data class LoginItem(
        @SerializedName("ac_id")
        val hr_id: Int,
        @SerializedName("en_id")
        val en_id: Int? = null,
        @SerializedName("cn_id")
        val pj_id: Int? = null,
        @SerializedName("ac_name")
        val hr_message: String,
        @SerializedName("ac_email")
        val hr_status: String,
        @SerializedName("ac_phone")
        val hr_date_confirm: String
    )
}