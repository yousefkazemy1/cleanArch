package com.example.authdatasource.remote.response

import com.example.core.model.Credential
import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("user_id")
    val userId: Long,

    @SerializedName("access_token")
    val token: String,

    @SerializedName("refresh_token")
    val refreshToken: String,

    @SerializedName("expries_in")
    val expire: Long
)

fun SignUpResponse.mapToCredential() = Credential(
    userId = this.userId,
    token = this.token,
    refreshToken = this.refreshToken
)