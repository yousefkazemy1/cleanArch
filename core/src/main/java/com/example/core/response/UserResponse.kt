package com.example.core.response

import com.example.core.model.Gender
import com.example.core.model.User
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id") val id: Long,

    @SerializedName("user_name") val userName: String,

    @SerializedName("name") val name: String,

    @SerializedName("profile_image") val profileImage: String? = null,

    @SerializedName("gender") val gender: String? = null,
)

fun UserResponse.mapToUser() = User(
    id = this.id,
    userName = this.userName,
    name = this.name,
    profileImage = this.profileImage,
    gender = this.gender?.mapToGenderType()
)

fun String.mapToGenderType(): Gender {
    return when (this) {
        "undefined" -> {
            Gender.NOTHING
        }
        "male" -> {
            Gender.MALE
        }
        "female" -> {
            Gender.FEMALE
        }
        "custom" -> {
            Gender.CUSTOM
        }
        else -> {
            Gender.NOTHING
        }
    }
}