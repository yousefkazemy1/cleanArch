package com.example.contentdatasource.remote.response

import com.example.core.response.MediaResponse
import com.example.core.response.UserResponse
import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("id")
    val id: Long,

    @SerializedName("caption")
    val caption: String,

    @SerializedName("media")
    val media: List<MediaResponse>,

    @SerializedName("user")
    val user: UserResponse
)