package com.miqdad71.starworks.api

import com.miqdad71.starworks.view.model.company.CompanyResponse
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET

interface CompanyAPI {
    @GET("company")
    suspend fun getAllCompany(): CompanyResponse
}