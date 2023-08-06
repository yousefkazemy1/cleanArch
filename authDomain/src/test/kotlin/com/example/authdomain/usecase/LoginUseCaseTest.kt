package com.example.authdomain.usecase

import com.example.authdomain.model.Auth
import com.example.authdomain.model.ValidationError
import com.example.authdomain.repository.AuthRepository
import com.example.core.model.Credential
import com.example.core.model.ErrorCause
import com.example.core.model.ErrorResult
import com.example.core.model.Result
import com.example.core.utils.logger.ConsoleLogger
import com.example.core.utils.logger.Logger
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class LoginUseCaseTest {

    private lateinit var repository: AuthRepository
    private lateinit var logger: Logger

    private val auth = Auth(
        email = "kkazemi640@gmail.com", password = "Yousef1996!"
    )

    val mockCredential = Credential(token = "")

    @BeforeEach
    fun setUp() = runBlocking {
        repository = Mockito.mock(AuthRepository::class.java)
        Mockito.`when`(repository.login(auth = auth))
            .thenReturn(Result.Success(mockCredential))

        logger = Mockito.spy(ConsoleLogger())
        Mockito.doNothing().`when`(logger).log()
    }

    @Test
    fun `login with bad format email, login error`() = runBlocking {
        val auth = Auth(
            email = "kkazemi640gmail.com", password = "Yousef199!"
        )

        val loginUseCase = LoginUseCase(repository = repository, logger = logger)

        val result = Result.Error(
            ErrorResult(
                errorCause = ErrorCause.VALIDATION, errorResponse = ValidationError.EMAIL.toString()
            )
        )
        assertEquals(loginUseCase.invoke(auth), result)
    }

    @Test
    fun `login with bad format password, login error`() = runBlocking {
        val auth = Auth(
            email = "kkazemi640@gmail.com", password = "yousef"
        )

        val loginUseCase = LoginUseCase(repository = repository, logger = logger)

        val result = Result.Error(
            ErrorResult(
                errorCause = ErrorCause.VALIDATION, errorResponse = ValidationError.PASSWORD.toString()
            )
        )

        assertEquals(loginUseCase.invoke(auth), result)
    }

    @Test
    fun `login with correct format email and password, login successfully`() = runBlocking {
        val loginUseCase = LoginUseCase(repository = repository, logger = logger)

        val result = Result.Success(mockCredential)

        assertEquals(loginUseCase.invoke(auth), result)
    }
}