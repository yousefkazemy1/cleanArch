package com.example.core.model

data class User(
    val id: Long,
    val userName: String,
    val name: String,
    val profileImage: String? = null,
    val gender: Gender? = null
)

enum class Gender {
    MALE, FEMALE, CUSTOM, NOTHING
}