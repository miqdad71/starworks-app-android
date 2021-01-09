package com.miqdad71.starworks.data.model.engineer

import com.miqdad71.starworks.data.model.engineer.HireProjectModel

data class HireProjectResponse(
    val success: String,
    val message: String,
    val data: List<HireProjectModel>
)
