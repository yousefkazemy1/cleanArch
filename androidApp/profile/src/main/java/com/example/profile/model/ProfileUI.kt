package com.example.profile.model

import com.example.core.model.UserUI
import com.example.profiledomain.model.Profile

data class ProfileUI(
    val user: UserUI,
    val email: String?,
    val phone: String?
)

fun Profile.convertToProfileUI() = ProfileUI(
    user = UserUI(
        id = this.user.id,
        username = this.user.userName,
        profileImage = this.user.profileImage,
        name = this.user.name,
    ),
    email = this.email,
    phone = this.phone,
)