package com.example.contentdatasource.remote

import com.example.contentdatasource.remote.response.HomePostResponse
import com.example.data.Endpoints
import retrofit2.Response
import retrofit2.http.GET

interface PostApi {

    @GET(Endpoints.GET_HOME_POSTS)
    suspend fun getHomePosts(): Response<HomePostResponse>
}