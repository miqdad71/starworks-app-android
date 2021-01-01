package com.miqdad71.starworks.project

import retrofit2.http.GET

interface ListEngineerApiService {
    @GET("engineer")
    suspend fun getAllEngineer(): ListEngineerResponse
}