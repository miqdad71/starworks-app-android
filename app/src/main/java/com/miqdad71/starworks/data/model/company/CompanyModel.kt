package com.miqdad71.starworks.data.model.company

data class CompanyModel(
    var acName: String? = null,
    var acEmail: String? = null,
    var acPhone: Long = 0,
    var acPassword: String? = null,
    var acPosition: String? = null,
    var acCompany: String? = null,
    var isLogin: Boolean = false
)