package com.example.profiledatasource.repository

import com.example.core.model.ErrorCause
import com.example.core.model.ErrorResult
import com.example.core.model.Result
import com.example.core.utils.convertToString
import com.example.data.utils.convertToErrorResponse
import com.example.profiledatasource.remote.ProfileApi
import com.example.profiledatasource.remote.response.mapToProfile
import com.example.profiledomain.model.Profile
import com.example.profiledomain.repository.ProfileRespository

class ProfileRepositoryImpl(
    private val profileApi: ProfileApi
) : ProfileRespository {
    override suspend fun getProfile(userId: Long): Result<Profile> {
        return try {
            profileApi.getProfile(userId = userId).let { response ->
                if (response.isSuccessful && response.body() != null) {
                    Result.Success(response.body()!!.mapToProfile())
                } else if (response.errorBody() != null && response.code() != 500) {
                    val result = response.errorBody()!!.byteStream().convertToString()
                        .convertToErrorResponse()
                    Result.Error(
                        ErrorResult(
                            errorCause = ErrorCause.API, errorResponse = result.message
                        )
                    )
                } else {
                    Result.Error(ErrorResult(errorCode = 500, errorCause = ErrorCause.API))
                }
            }
        } catch (e: Exception) {
            Result.Error(
                ErrorResult(
                    errorCode = 500,
                    errorCause = ErrorCause.EXCEPTION,
                    errorResponse = e.message ?: ""
                )
            )
        }
    }
}