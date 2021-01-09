package com.miqdad71.starworks.data.model.company

data class HomeCompanyResponse(
    val success: String,
    val message: String,
    val data: List<HomeEngineerModel>
)
