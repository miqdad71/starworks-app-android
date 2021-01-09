package com.miqdad71.starworks.data.model.engineer

import com.google.gson.annotations.SerializedName

data class HireProjectModel(
    @SerializedName("hr_id")
    val hrId: Int,

    @SerializedName("en_id")
    val enId: Int,

    @SerializedName("pj_id")
    val pjId: Int,

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