package com.miqdad71.starworks.serviceapi

import com.miqdad71.starworks.data.model.project.ProjectResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ProjectAPI {
    @GET("project/{cnId}")
    suspend fun getAllProject(
        @Path("cnId") cnId: Int
    ): ProjectResponse

    @FormUrlEncoded
    @POST("project")
    suspend fun createProject(
        @Field("cn_id") cnId: Int,
        @Field("pj_project_name") pjProjectName: String,
        @Field("pj_description") pjDescription: String,
        @Field("pj_deadline") pjDeadline: String,
        @Field ("image") image: String? = null
    ): ProjectResponse

    @Multipart
    @PUT("project/{pjId}")
    suspend fun updateProject(
        @Path("pjId") pjId: Int,
        @Part("pj_project_name") pjProjectName: RequestBody,
        @Part("pj_description") pjDescription: RequestBody,
        @Part("pj_deadline") pjDeadline: RequestBody,
        @Part image: MultipartBody.Part? = null
    ): ProjectResponse

    @DELETE("project/{pjId}")
    suspend fun deleteProject(
        @Path("pjId") pjId: Int
    ): ProjectResponse
}