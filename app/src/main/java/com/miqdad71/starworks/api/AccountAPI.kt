package com.miqdad71.starworks.api

import com.miqdad71.starworks.data.model.account.AccountResponse
import com.miqdad71.starworks.data.model.account.LoginResponse
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

    @FormUrlEncoded
    @POST("account")
    suspend fun signUpEngineerAccount(
        @Field("ac_name") acName: String,
        @Field("ac_email") acEmail: String,
        @Field("ac_phone") acPhone: String,
        @Field("ac_password") acPassword: String,
        @Field("ac_level") acLevel: Int
    ): AccountResponse

    @FormUrlEncoded
    @POST("account")
    suspend fun signUpCompanyAccount(
        @Field("ac_name") acName: String,
        @Field("ac_email") acEmail: String,
        @Field("ac_phone") acPhone: String,
        @Field("ac_password") acPassword: String,
        @Field("ac_level") acLevel: Int,
        @Field("cn_company") cnCompany: String,
        @Field("cn_position") cnPosition: String
    ): AccountResponse
}