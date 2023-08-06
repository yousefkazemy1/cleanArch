package com.example.core.model

data class UserUI(
    val id: Long,
    val username: String,
    val profileImage: String? = null,
    val name: String? = null
)