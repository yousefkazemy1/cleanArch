package com.example.data.remote.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("error")
    val error: String?,

    @SerializedName("errors")
    val errors: String?,

    @SerializedName("message")
    val message: String,
)