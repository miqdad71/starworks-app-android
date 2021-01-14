package com.miqdad71.starworks.serviceapi

import com.miqdad71.starworks.data.model.company.CompanyResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface CompanyAPI {
    @GET("company")
    suspend fun getAllCompany(): CompanyResponse

    @GET("company/detail/{acId}")
    suspend fun getDetailCompany(
        @Path("acId") acId: Int
    ): CompanyResponse

    @FormUrlEncoded
    @PUT("company/{cnId}")
    suspend fun updateCompany(
        @Path("cnId") cnId: Int,
        @FieldMap fields: Map<String, String>
    ): CompanyResponse

    @Multipart
    @PUT("company/{cnId}")
    suspend fun updateCompanyImage(
        @Path("cnId") cnId: Int,
        @Part image: MultipartBody.Part? = null
    ): CompanyResponse
}