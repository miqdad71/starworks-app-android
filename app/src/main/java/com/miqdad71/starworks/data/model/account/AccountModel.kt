package com.miqdad71.starworks.data.model.account

data class AccountModel(
    val ac_id: Int? = 0,
    val ac_name: String? = null,
    val ac_email: String? = null,
    val ac_phone: String? = null,
    val ac_password: String? = null,
    val ac_level: Int? = 0,
    val token: String? = null
)