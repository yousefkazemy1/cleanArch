package com.example.authdomain.model

data class Auth(
    val username: String? = null,
    val email: String? = null,
    val password: String,
    val name: String? = null
)
