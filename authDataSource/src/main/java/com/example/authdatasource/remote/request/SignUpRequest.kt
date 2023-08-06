package com.example.authdatasource.remote.request

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("email")
    val username: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("name")
    val name: String? = null
)