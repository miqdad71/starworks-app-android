package com.miqdad71.starworks.data.model.company

import com.miqdad71.starworks.data.model.engineer.HomeEngineerModel

data class HomeCompanyResponse(
    val success: String,
    val message: String,
    val data: List<HomeEngineerModel>
)
