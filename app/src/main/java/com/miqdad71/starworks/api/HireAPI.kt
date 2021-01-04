package com.miqdad71.starworks.api

import com.miqdad71.starworks.data.model.account.LoginResponse
import com.miqdad71.starworks.data.model.hire.HireResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface HireAPI {
    @FormUrlEncoded
//    endpoint
    @POST("hire")
    suspend fun hire(
        @Field("en_id") enId: Int,
        @Field("pj_id") pjId: Int,
        @Field("hr_price") hrPrice: String,
        @Field("hr_message") hrMessage: String
        ): HireResponse
}