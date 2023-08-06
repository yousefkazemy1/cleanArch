package com.example.profiledatasource.remote

import com.example.data.Endpoints
import com.example.profiledatasource.remote.response.ProfileResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProfileApi {
    @GET(Endpoints.GET_PROFILE)
    suspend fun getProfile(
        @Query("user_id") userId: Long
    ): Response<ProfileResponse>
}