package com.miqdad71.starworks.data.model.engineer

data class EngineerModel(
    var acName: String? = null,
    var acEmail: String? = null,
    var acPhone: Long = 0,
    var acPassword: String? = null,
    var isLogin: Boolean = false
)