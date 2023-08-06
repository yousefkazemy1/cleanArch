package com.example.profiledomain.usecase

import com.example.core.model.Result
import com.example.core.utils.login.LoginStatus
import com.example.profiledomain.model.Profile
import com.example.profiledomain.repository.ProfileRespository

class GetProfileUseCase(
    private val repository: ProfileRespository,
    private val loginStatus: LoginStatus
) {
    suspend operator fun invoke(userId: Long): Result<Profile> {
        if (!loginStatus.isUserLoggedIn) {
            throw RuntimeException("User hasn't logged in to get user information")
        }

        return repository.getProfile(userId)
    }
}