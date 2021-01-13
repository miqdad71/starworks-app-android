package com.miqdad71.starworks.serviceapi

import com.miqdad71.starworks.data.model.hire.HireResponse
import retrofit2.http.*

interface HireAPI {

    @GET("hire/engineer/{enId}")
    suspend fun getAllHire(
        @Path("enId") enId: Int
    ): HireResponse

    @GET("hire/company/{cnId}")
    suspend fun getAllHireCompany(
        @Path("cnId") cnId: Int
    ): HireResponse

    @FormUrlEncoded
    @POST("hire")
    suspend fun hire(
        @Field("en_id") enId: Int.Companion,
        @Field("pj_id") pjId: Int.Companion,
        @Field("hr_price") hrPrice: Long.Companion,
        @Field("hr_message") hrMessage: String.Companion
    ): HireResponse

    @FormUrlEncoded
    @PUT("hire/{hrId}")
    suspend fun updateHireStatus(
        @Path("hrId") hrId: Int,
        @Field("hr_status") hrStatus: String
    ): HireResponse
}