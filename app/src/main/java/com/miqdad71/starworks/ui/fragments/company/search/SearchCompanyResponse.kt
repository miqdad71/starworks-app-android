package com.miqdad71.starworks.ui.fragments.company.search

import com.google.gson.annotations.SerializedName

data class SearchCompanyResponse(
    val success: String,
    val message: String,
    val data: List<EngineerList>
) {
    data class EngineerList(
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
    )
}