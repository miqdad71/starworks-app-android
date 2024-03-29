package com.miq71.starworks.service

import com.miq71.starworks.model.hire.HireResponse
import retrofit2.http.*

interface HireApiService {
    @GET("hire/engineer/{enId}")
    suspend fun getAllHire(
        @Path("enId") enId: Int
    ): HireResponse

    @GET("hire/company/{cnId}")
    suspend fun getAllHireCompany(
        @Path("cnId") cnId: Int,
        @Query("status") status: String
    ): HireResponse

    @GET("hire/project/{pjId}")
    suspend fun getAllHireByProject(
        @Path("pjId") pjId: Int
    ): HireResponse

    @GET("hire/check")
    suspend fun checkIsHire(
        @Query("enId") enId: Int,
        @Query("cnId") cnId: Int
    ): HireResponse

    @FormUrlEncoded
    @POST("hire")
    suspend fun createHire(
        @Field("en_id") enId: Int,
        @Field("pj_id") pjId: Int,
        @Field("hr_price") hrPrice: Long,
        @Field("hr_message") hrMessage: String
    ): HireResponse

    @FormUrlEncoded
    @PUT("hire/{hrId}")
    suspend fun updateHireStatus(
        @Path("hrId") hrId: Int,
        @Field("hr_status") hrStatus: String
    ): HireResponse
}