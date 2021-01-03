package com.miqdad71.starworks.ui.fragments.company.homegetdata

import com.google.gson.annotations.SerializedName

data class HomeEngineerModel(
    @SerializedName("en_id")
    val enId: String,

    @SerializedName("ac_id")
    val acId: String,

    @SerializedName("ac_name")
    val acName: String,

    @SerializedName("en_job_title")
    val enJobTitle: String,

    @SerializedName("en_job_type")
    val enJobType: String,

    @SerializedName("en_domicile")
    val enDomicile: String,

    @SerializedName("en_description")
    val enDescription: String,

    @SerializedName("en_profile")
    val enProfile: String
//    val enId: String? = null,
//    val acId: String? = null,
//    val acName: String? = null,
//    val enJobTitle: String? = null,
//    val enJobType: String? = null,
//    val enDomicile: String? = null,
//    val enDescription: String? = null,
//    val enProfile: String? = null
)