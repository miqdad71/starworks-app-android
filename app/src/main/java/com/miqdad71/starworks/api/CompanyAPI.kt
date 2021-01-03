package com.miqdad71.starworks.api

import com.miqdad71.starworks.data.model.company.CompanyResponse
import retrofit2.http.GET

interface CompanyAPI {
    @GET("company")
    suspend fun getAllCompany(): CompanyResponse
}