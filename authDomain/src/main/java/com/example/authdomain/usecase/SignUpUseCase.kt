package com.example.authdomain.usecase

import com.example.authdomain.model.Auth
import com.example.authdomain.model.ValidationError
import com.example.authdomain.repository.AuthRepository
import com.example.core.model.ErrorCause
import com.example.core.model.ErrorResult
import com.example.core.model.Result
import com.example.core.model.SuccessResult
import com.example.core.utils.logger.Logger
import com.example.core.validateEmail
import com.example.core.validatePassword

class SignUpUseCase(
    private val repository: AuthRepository,
    private val logger: Logger,
) {
    suspend operator fun invoke(auth: Auth): Result<SuccessResult> {
        logger.log("SignUpUseCase")
        if (auth.email == null || !auth.email.validateEmail()) {
            logger.log("SignUpUseCase", "can not validate email")
            return Result.Error(
                ErrorResult(
                    errorCause = ErrorCause.VALIDATION,
                    errorResponse = ValidationError.EMAIL.toString()
                )
            )
        }

        if (!auth.password.validatePassword()) {
            logger.log("SignUpUseCase", "can not validate password")
            return Result.Error(
                ErrorResult(
                    errorCause = ErrorCause.VALIDATION,
                    errorResponse = ValidationError.PASSWORD.toString()
                )
            )
        }

        if (auth.name == null || auth.name.isEmpty()) {
            logger.log("SignUpUseCase", "can not validate name")
            return Result.Error(
                ErrorResult(
                    errorCause = ErrorCause.VALIDATION,
                    errorResponse = ValidationError.NAME.toString()
                )
            )
        }

        val result = repository.signUp(auth)

        return if (result is Result.Success) {
            Result.Success(SuccessResult())
        } else {
            Result.Error((result as Result.Error).error)
        }
    }
}