package com.miqdad71.starworks.api

import com.miqdad71.starworks.ui.fragments.company.home.HomeCompanyResponse
import retrofit2.http.GET

interface EngineerAPI {
    @GET("engineer")
    suspend fun getAllEngineer(): HomeCompanyResponse
}