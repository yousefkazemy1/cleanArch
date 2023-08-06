package com.example.authdatasource.remote.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("grant_type")
    val grantType: String = "password",

    @SerializedName("email")
    val username: String,

    @SerializedName("password")
    val password: String
)