package com.miqdad71.starworks.tryweek7

import retrofit2.http.GET

interface ListEngineerApiService {
    @GET("engineer")
    suspend fun getAllEngineer(): ListEngineerResponse
}