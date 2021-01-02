package com.miqdad71.starworks.project

import com.google.gson.annotations.SerializedName

data class ListEngineerModel(
    val enId: String?= null,
    val acId: String?= null,
    val acName: String?= null,
    val enJobTitle: String?= null,
    val enJobType: String?= null,
    val enDomicile: String?= null,
    val enDescription: String?= null,
    val enProfile: String?= null
)