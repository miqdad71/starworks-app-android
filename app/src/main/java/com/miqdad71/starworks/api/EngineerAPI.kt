package com.miqdad71.starworks.api

import com.miqdad71.starworks.view.model.account.LoginResponse
import com.miqdad71.starworks.view.model.engineer.EngineerResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface EngineerAPI {
    @FormUrlEncoded
    @GET("engineer")
    suspend fun getAllEngineer(): EngineerResponse
}