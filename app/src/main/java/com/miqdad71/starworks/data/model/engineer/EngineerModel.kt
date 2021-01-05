package com.miqdad71.starworks.data.model.engineer

data class EngineerModel(
    var acName: String? = null,
    var acEmail: String? = null,
    var acPassword: String? = null,
    var acPhone: Long = 0,
    var acPosition: String? = null,
    var acCompany: String? = null,
    var isLogin: Boolean = false
)