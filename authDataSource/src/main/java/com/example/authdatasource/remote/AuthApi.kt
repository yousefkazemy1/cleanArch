package com.example.authdatasource.remote

import com.example.authdatasource.remote.request.LoginRequest
import com.example.authdatasource.remote.request.SignUpRequest
import com.example.authdatasource.remote.response.LoginResponse
import com.example.authdatasource.remote.response.SignUpResponse
import com.example.data.Endpoints
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST(Endpoints.LOGIN)
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST(Endpoints.SIGN_UP)
    suspend fun signUp(
        @Body request: SignUpRequest
    ): Response<SignUpResponse>
}