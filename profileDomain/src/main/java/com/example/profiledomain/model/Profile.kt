package com.example.profiledomain.model

import com.example.core.model.User

data class Profile(
    val user: User,
    val email: String,
    val phone: String
)