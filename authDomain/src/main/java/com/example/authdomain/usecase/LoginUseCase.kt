package com.example.authdomain.usecase

import com.example.authdomain.model.Auth
import com.example.authdomain.model.ValidationError
import com.example.authdomain.repository.AuthRepository
import com.example.core.model.Credential
import com.example.core.model.ErrorCause
import com.example.core.model.ErrorResult
import com.example.core.model.Result
import com.example.core.utils.logger.Logger
import com.example.core.validateEmail
import com.example.core.validatePassword

class LoginUseCase(
    private val repository: AuthRepository,
    private val logger: Logger
) {

    suspend operator fun invoke(auth: Auth): Result<Credential> {
        logger.log("LoginUseCase")
        if (auth.email == null || !auth.email.validateEmail()) {
            logger.log("LoginUseCase", "can not validate email")
            return Result.Error(
                ErrorResult(
                    errorCause = ErrorCause.VALIDATION,
                    errorResponse = ValidationError.EMAIL.toString()
                )
            )
        }

        if (!auth.password.validatePassword()) {
            logger.log("LoginUseCase", "can not validate password")
            return Result.Error(
                ErrorResult(
                    errorCause = ErrorCause.VALIDATION,
                    errorResponse = ValidationError.PASSWORD.toString()
                )
            )
        }

        val result = repository.login(auth)

        return if (result is Result.Success) {
            Result.Success(result.data)
        } else {
            Result.Error((result as Result.Error).error)
        }
    }
}