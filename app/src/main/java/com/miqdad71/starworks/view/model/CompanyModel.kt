package com.miqdad71.starworks.view.model

data class CompanyModel(
    var name: String? = null,
    var email: String? = null,
    var company: String? = null,
    var position: String? = null,
    var phone: Long = 0,
    var password: String? = null,
    var isLogin: Boolean = false
)