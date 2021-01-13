package com.miqdad71.starworks.serviceapi

import com.miqdad71.starworks.data.model.skill.SkillResponse
import retrofit2.http.*

interface SkillAPI {
    @GET("skill/{enId}")
    suspend fun getAllSkill(
        @Path("enId") enId: Int
    ): SkillResponse

    @FormUrlEncoded
    @POST("skill")
    suspend fun createSkill(
        @Field("en_id") enId: Int,
        @Field("sk_skill_name") skSkillName: String
    ): SkillResponse

    @FormUrlEncoded
    @PUT("skill/{skId}")
    suspend fun updateSkill(
        @Path("skId") skId: Int,
        @Field("sk_skill_name") skSkillName: String
    ): SkillResponse

    @DELETE("skill/{skId}")
    suspend fun deleteSkill(
        @Path("skId") skId: Int
    ): SkillResponse
}