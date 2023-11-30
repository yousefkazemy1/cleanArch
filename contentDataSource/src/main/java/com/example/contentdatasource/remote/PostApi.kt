package com.example.contentdatasource.remote

import com.example.contentdatasource.remote.response.HomePostResponse
import com.example.contentdatasource.remote.response.fake.HomePostResponseFake
import com.example.data.Endpoints
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.Url

val PIXEL_URL = "https://api.pexels.com/videos/search"
val PIXEL_TOKEN = "a5TRTJaKpw1uvLXf9RYcuXWGrQsW8EwQWJLK5XeOyfc1KAJsHZ7mGk4M"
interface PostApi {

    @GET(Endpoints.GET_HOME_POSTS)
    suspend fun getHomePosts(): Response<HomePostResponse>

    @GET
    suspend fun getHomePosts(
        @Url url: String = PIXEL_URL,
        @Header("Authorization") token: String = PIXEL_TOKEN,
        @Query("query") query: String = "nature",
        @Query("per_page") perPage: Int,
    ): Response<HomePostResponseFake>
}