package com.example.core.model

data class Credential(
    val userId: Long = 0,
    val token: String,
    val refreshToken: String? = null,
    val password: String? = null,
    val name: String? = null
)