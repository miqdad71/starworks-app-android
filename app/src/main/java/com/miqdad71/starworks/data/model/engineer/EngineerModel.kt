package com.miqdad71.starworks.data.model.engineer

data class EngineerModel(
    var name: String? = null,
    var email: String? = null,
    var phone: Long = 0,
    var password: String? = null,
    var isLogin: Boolean = false
)