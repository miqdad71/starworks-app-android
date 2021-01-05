package com.miqdad71.starworks.ui.fragments.engineer.hire

import com.google.gson.annotations.SerializedName

data class HireProjectModel(
    @SerializedName("hr_id")
    val hrId: String,

    @SerializedName("en_id")
    val enId: String,

    @SerializedName("pj_id")
    val pjId: String,

    @SerializedName("pj_project_name")
    val pjProjectName: String,

    @SerializedName("pj_description")
    val pjDescription: String,

    @SerializedName("hr_price")
    val hrPrice: String,

    @SerializedName("hr_message")
    val hrMessage: String,

    @SerializedName("hr_status")
    val hrStatus: String,

    @SerializedName("hr_date_confirm")
    val hrDateConfirm: String,

    @SerializedName("hr_created_at")
    val hrCreatedAt: String
)