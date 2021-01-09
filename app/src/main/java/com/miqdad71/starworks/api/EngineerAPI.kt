package com.miqdad71.starworks.api

import com.miqdad71.starworks.data.model.company.HomeCompanyResponse
import retrofit2.http.GET

interface EngineerAPI {
    @GET("engineer")
    suspend fun getAllEngineer(): HomeCompanyResponse
}