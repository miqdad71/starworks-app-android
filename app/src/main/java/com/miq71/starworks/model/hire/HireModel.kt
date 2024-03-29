package com.miq71.starworks.model.hire

data class HireModel(
    val hrId: Int? = 0,
    val enId: Int? = 0,
    val pjId: Int? = 0,
    val hrPrice: Long? = 0,
    val hrMessage: String? = null,
    val hrStatus: String? = null,
    val hrDateConfirm: String? = null,
    val pjProjectName: String? = null,
    val pjDescription: String? = null,
    val pjDeadline: String? = null,
    val pjImage: String? = null,
    val cnCompany: String? = null,
    val cnField: String? = null,
    val cnCity: String? = null,
    val cnProfile: String? = null,
    val enProfile: String? = null,
    val acName: String? = null,
    val acEmail: String? = null,
    val acPhone: String? = null
)