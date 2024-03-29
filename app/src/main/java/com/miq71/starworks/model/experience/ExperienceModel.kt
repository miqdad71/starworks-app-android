package com.miq71.starworks.model.experience

data class ExperienceModel(
    val ex_id: Int? = 0,
    val en_id: Int? = 0,
    val ex_position: String? = null,
    val ex_company: String? = null,
    val ex_start: String? = null,
    val ex_end: String? = null,
    val ex_description: String? = null
)