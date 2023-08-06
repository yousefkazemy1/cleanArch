package com.example.authdatasource.repository

import com.example.authdatasource.remote.AuthApi
import com.example.authdatasource.remote.request.LoginRequest
import com.example.authdatasource.remote.request.SignUpRequest
import com.example.authdatasource.remote.response.mapToCredential
import com.example.authdomain.model.Auth
import com.example.authdomain.repository.AuthRepository
import com.example.core.model.Credential
import com.example.core.model.ErrorCause
import com.example.core.model.ErrorResult
import com.example.core.model.Result
import com.example.core.utils.convertToString
import com.example.core.utils.logger.Logger
import com.example.data.utils.convertToErrorResponse
import javax.inject.Named

class AuthRepositoryImpl(
    private val authApi: AuthApi,
    @Named("console_logger") private val logger: Logger,
) : AuthRepository {
    override suspend fun login(auth: Auth): Result<Credential> {
        return try {
            authApi.login(
                request = LoginRequest(
                    username = auth.email ?: auth.username ?: throw Exception("email or username is required to log in"),
                    password = auth.password
                )
            ).let { response ->
                if (response.isSuccessful && response.body() != null) {
                    Result.Success(response.body()!!.mapToCredential())
                } else if (response.errorBody() != null && response.code() != 500) {
                    val result =
                        response.errorBody()!!.byteStream().convertToString().convertToErrorResponse()
                    logger.log(
                        "AuthRepositoryImpl:login",
                        "error: ${result.error}",
                        "message: ${result.message}",
                    )
                    Result.Error(
                        ErrorResult(
                            errorCause = ErrorCause.API,
                            errorResponse = result.message
                        )
                    )
                } else {
                    Result.Error(ErrorResult(errorCode = 500, errorCause = ErrorCause.API))
                }
            }
        } catch (e: Exception) {
            Result.Error(ErrorResult(errorCode = 500, errorCause = ErrorCause.EXCEPTION, errorResponse = e.message ?: ""))
        }
    }

    override suspend fun signUp(auth: Auth): Result<Credential> {
        return try {
            authApi.signUp(
                request = SignUpRequest(
                    username = auth.email ?: auth.username ?: throw Exception("email or username is required to log in"),
                    password = auth.password,
                    name = auth.name
                )
            ).let { response ->
                if (response.isSuccessful && response.body() != null) {
                    Result.Success(response.body()!!.mapToCredential())
                } else if (response.errorBody() != null && response.code() != 500) {
                    val result =
                        response.errorBody()!!.byteStream().convertToString().convertToErrorResponse()
                    logger.log(
                        "AuthRepositoryImpl:signUp",
                        "error: ${result.errors}",
                        "message: ${result.message}",
                    )
                    Result.Error(
                        ErrorResult(
                            errorCause = ErrorCause.API,
                            errorResponse = result.message
                        )
                    )
                } else {
                    Result.Error(ErrorResult(errorCode = 500, errorCause = ErrorCause.API))
                }
            }
        } catch (e: Exception) {
            Result.Error(ErrorResult(errorCode = 500, errorCause = ErrorCause.EXCEPTION, errorResponse = e.message ?: ""))
        }
    }
}