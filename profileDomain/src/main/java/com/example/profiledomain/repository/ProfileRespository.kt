package com.example.profiledomain.repository

import com.example.core.model.Result
import com.example.profiledomain.model.Profile

interface ProfileRespository {
    suspend fun getProfile(userId: Long): Result<Profile>
}