package com.example.profiledatasource.repository.fake

import com.example.core.model.Gender
import com.example.core.model.Result
import com.example.core.model.User
import com.example.profiledomain.model.Profile
import com.example.profiledomain.repository.ProfileRespository

class ProfileRepositoryImplFake : ProfileRespository {
    override suspend fun getProfile(userId: Long): Result<Profile> =
        Result.Success(
            Profile(
                user = User(
                    id = 1,
                    userName = "username1",
                    name = "name1",
                    gender = Gender.MALE
                ),
                email = "email1@gmail.com",
                phone = "1234567890"
            )
        )
}