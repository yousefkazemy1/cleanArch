package com.example.authdomain.usecase

import com.example.authdomain.model.Auth
import com.example.authdomain.model.ValidationError
import com.example.authdomain.repository.AuthRepository
import com.example.core.model.*
import com.example.core.utils.logger.ConsoleLogger
import com.example.core.utils.logger.Logger
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class SignUpUseCaseTest {

    private lateinit var repository: AuthRepository
    private lateinit var logger: Logger

    private val auth = Auth(
        email = "kkazemi640@gmail.com", password = "Yousef1996!", name = "yousef"
    )

    @BeforeEach
    fun setUp() = runBlocking {
        repository = Mockito.mock(AuthRepository::class.java)
        Mockito.`when`(repository.signUp(auth = auth))
            .thenReturn(Result.Success(Credential(token = "")))

        logger = Mockito.spy(ConsoleLogger())
        Mockito.doNothing().`when`(logger).log()
    }

    @Test
    fun `sign up with bad format email, sign up error`() = runBlocking {
        val auth = Auth(
            email = "kkazemi640gmail.com", password = "Yousef199!"
        )

        val signUpUseCase = SignUpUseCase(repository = repository, logger = logger)

        val result = Result.Error(
            ErrorResult(
                errorCause = ErrorCause.VALIDATION, errorResponse = ValidationError.EMAIL.toString()
            )
        )
        assertEquals(signUpUseCase.invoke(auth), result)
    }

    @Test
    fun `sign up with bad format password, sign up error`() = runBlocking {
        val auth = Auth(
            email = "kkazemi640@gmail.com", password = "youse"
        )

        val signUpUseCase = SignUpUseCase(repository = repository, logger = logger)

        val result = Result.Error(
            ErrorResult(
                errorCause = ErrorCause.VALIDATION, errorResponse = ValidationError.PASSWORD.toString()
            )
        )

        assertEquals(signUpUseCase.invoke(auth), result)
    }

    @Test
    fun `sign up with empty name, sign up error`() = runBlocking {
        val auth = Auth(
            email = "kkazemi640@gmail.com", password = "Yousef1996!", name = ""
        )

        val signUpUseCase = SignUpUseCase(repository = repository, logger = logger)

        val result = Result.Error(
            ErrorResult(
                errorCause = ErrorCause.VALIDATION, errorResponse = ValidationError.NAME.toString()
            )
        )

        assertEquals(signUpUseCase.invoke(auth), result)
    }

    @Test
    fun `sign up with correct format email and password, sign up successfully`() = runBlocking {
        val signUpUseCase = SignUpUseCase(repository = repository, logger = logger)

        val result = Result.Success(SuccessResult())

        assertEquals(signUpUseCase.invoke(auth), result)
    }
}