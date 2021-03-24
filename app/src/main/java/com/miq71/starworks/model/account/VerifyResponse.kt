package com.miq71.starworks.model.account

import com.google.gson.annotations.SerializedName

data class VerifyResponse(val success: Boolean, val message: String, val data: List<AccountItem>) {
    data class AccountItem(
        @SerializedName("ac_id")
        val acId: Int,
    )
}