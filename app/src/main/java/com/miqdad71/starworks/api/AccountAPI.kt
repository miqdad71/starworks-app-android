package com.miqdad71.starworks.api

import com.miqdad71.starworks.view.model.account.LoginResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AccountAPI {
    @FormUrlEncoded
//    endpoint
    @POST("account/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String

    ): LoginResponse
}