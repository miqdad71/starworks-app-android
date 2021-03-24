package com.miq71.starworks.model.project

data class ProjectModel(
    val pjId: Int? = 0,
    val cnId: Int? = 0,
    val pjProjectName: String? = null,
    val pjDescription: String? = null,
    val pjDeadline: String? = null,
    val pjImage: String? = null
)