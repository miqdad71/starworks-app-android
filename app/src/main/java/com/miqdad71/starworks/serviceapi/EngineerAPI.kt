package com.miqdad71.starworks.serviceapi

import com.miqdad71.starworks.data.model.engineer.EngineerResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface EngineerAPI {
    @GET("engineer")
    suspend fun getAllEngineer(
        @Query("search") search: String? = null,
        @Query("limit") limit: Int? = null,
        @Query("page") page: Int? = null
    ): EngineerResponse

    @GET("engineer/filter")
    suspend fun getFilterEngineer(
        @Query("filter") filter: Int? = null,
        @Query("limit") limit: Int? = null,
        @Query("page") page: Int? = null
    ): EngineerResponse

    @GET("engineer/detail/{acId}")
    suspend fun getDetailEngineer(
        @Path("acId") acId: Int
    ): EngineerResponse

    @FormUrlEncoded
    @PUT("engineer/{enId}")
    suspend fun updateEngineer(
        @Path("enId") enId: Int,
        @Field("en_job_title") enJobTitle: String,
        @Field("en_job_type") enJobType: String,
        @Field("en_domicile") enDomicile: String,
        @Field("en_description") enDescription: String
    ): EngineerResponse

    @Multipart
    @PUT("engineer/{enId}")
    suspend fun updateEngineerImage(
        @Path("enId") enId: Int,
        @Part image: MultipartBody.Part? = null
    ): EngineerResponse
}