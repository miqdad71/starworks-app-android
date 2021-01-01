package com.miqdad71.starworks.project

import com.google.gson.annotations.SerializedName

data class ListEngineerModel(
    val enId: String?,
    val acId: String?,
    val acName: String?,
    val enJobTitle: String?,
    val enJobType: String?,
    val enDomicile: String?,
    val enDescription: String?,
    val enProfile: String?
)