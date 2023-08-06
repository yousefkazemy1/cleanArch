package com.example.profiledatasource.remote.response

import com.example.core.response.UserResponse
import com.example.core.response.mapToUser
import com.example.profiledomain.model.Profile
import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("user") val user: UserResponse,

    @SerializedName("email") val email: String,

    @SerializedName("phone") val phone: String,
)

fun ProfileResponse.mapToProfile() = Profile(
    user = this.user.mapToUser(),
    email = this.email,
    phone = this.phone
)