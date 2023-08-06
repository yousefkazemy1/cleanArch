package com.example.auth.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.auth.MainDispatcherRule
import com.example.auth.R
import com.example.auth.ui.CredentialRepositoryImplFake
import com.example.authdomain.model.Auth
import com.example.authdomain.model.ValidationError
import com.example.authdomain.usecase.LoginUseCase
import com.example.authdomain.usecase.SaveCredentialUseCase
import com.example.core.model.Credential
import com.example.core.model.ErrorCause
import com.example.core.model.ErrorResult
import com.example.core.model.Result
import com.example.core.utils.logger.ConsoleLogger
import com.example.core.utils.logger.Logger
import com.example.core.utils.login.LoginStatus
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class LoginViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    lateinit var logger: Logger
    lateinit var mockSaveCredentialUseCase: SaveCredentialUseCase

    val mockCredential = Credential(token = "")

    @Before
    fun setUp() {
        logger = Mockito.spy(ConsoleLogger())
        Mockito.doNothing().`when`(logger).log()

        val mockCredentialRepository = Mockito.spy(CredentialRepositoryImplFake())
        Mockito.doNothing().`when`(mockCredentialRepository).saveCredential(mockCredential)

        mockSaveCredentialUseCase = Mockito.spy(SaveCredentialUseCase(
            repository = mockCredentialRepository
        ))
        Mockito.doNothing().`when`(mockSaveCredentialUseCase).invoke(mockCredential)
    }

    @Test
    fun `login with correct credential, set snackbar message successfully`() = runBlocking {
        val loginUseCase = Mockito.mock(LoginUseCase::class.java)
        Mockito.`when`(
            loginUseCase.invoke(
                auth = Auth(email = "", password = "")
            )
        ).thenReturn(
            Result.Success(
                mockCredential
            )
        )

        val viewModel = LoginViewModel(
            useCase = loginUseCase,
            saveCredentialUseCase = mockSaveCredentialUseCase,
            logger = logger,
            loginStatus = LoginStatus
        )
        viewModel.login()

        viewModel.snackBarMessage.test {
            assertEquals(R.string.logged_in_successfully, awaitItem().messageId)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `login with wrong email, set snackbar message with email validation error`() = runBlocking {
        val loginUseCase = Mockito.mock(LoginUseCase::class.java)
        Mockito.`when`(
            loginUseCase.invoke(
                auth = Auth(email = "", password = "")
            )
        ).thenReturn(
            Result.Error(
                ErrorResult(
                    errorCause = ErrorCause.VALIDATION,
                    errorResponse =  ValidationError.EMAIL.toString()
                )
            )
        )

        val viewModel = LoginViewModel(
            useCase = loginUseCase,
            saveCredentialUseCase = mockSaveCredentialUseCase,
            logger = logger,
            loginStatus = LoginStatus
        )
        viewModel.login()

        viewModel.snackBarMessage.test {
            assertEquals(R.string.email_validation_error_message, awaitItem().messageId)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `login with wrong password, set snackbar message with password validation error`() = runBlocking {
        val loginUseCase = Mockito.mock(LoginUseCase::class.java)
        Mockito.`when`(
            loginUseCase.invoke(
                auth = Auth(email = "", password = "")
            )
        ).thenReturn(
            Result.Error(
                ErrorResult(
                    errorCause = ErrorCause.VALIDATION,
                    errorResponse =  ValidationError.PASSWORD.toString()
                )
            )
        )

        val viewModel = LoginViewModel(
            useCase = loginUseCase,
            saveCredentialUseCase = mockSaveCredentialUseCase,
            logger = logger,
            loginStatus = LoginStatus
        )
        viewModel.login()

        viewModel.snackBarMessage.test {
            assertEquals(R.string.password_validation_error_message, awaitItem().messageId)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `login with wrong credential, set snackbar message with api message error`() = runBlocking {
        val apiMessageError = "credntial.error"
        val loginUseCase = Mockito.mock(LoginUseCase::class.java)
        Mockito.`when`(
            loginUseCase.invoke(
                auth = Auth(email = "", password = "")
            )
        ).thenReturn(
            Result.Error(
                ErrorResult(
                    errorCause = ErrorCause.API,
                    errorCode =  422,
                    errorResponse = apiMessageError
                )
            )
        )

        val viewModel = LoginViewModel(
            useCase = loginUseCase,
            saveCredentialUseCase = mockSaveCredentialUseCase,
            logger = logger,
            loginStatus = LoginStatus
        )
        viewModel.login()

        viewModel.snackBarMessage.test {
            assertEquals(apiMessageError, awaitItem().message)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `login and get server error, set snackbar message with error message`() = runBlocking {
        val loginUseCase = Mockito.mock(LoginUseCase::class.java)
        Mockito.`when`(
            loginUseCase.invoke(
                auth = Auth(email = "", password = "")
            )
        ).thenReturn(
            Result.Error(
                ErrorResult(
                    errorCause = ErrorCause.API,
                    errorCode =  500
                )
            )
        )

        val viewModel = LoginViewModel(
            useCase = loginUseCase,
            saveCredentialUseCase = mockSaveCredentialUseCase,
            logger = logger,
            loginStatus = LoginStatus
        )
        viewModel.login()

        viewModel.snackBarMessage.test {
            assertEquals(R.string.error_in_server, awaitItem().messageId)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `login and get unknown exception, set snackbar message with error message`() = runBlocking {
        val loginUseCase = Mockito.mock(LoginUseCase::class.java)
        Mockito.`when`(
            loginUseCase.invoke(
                auth = Auth(email = "", password = "")
            )
        ).thenReturn(
            Result.Error(
                ErrorResult(
                    errorCause = ErrorCause.EXCEPTION
                )
            )
        )

        val viewModel = LoginViewModel(
            useCase = loginUseCase,
            saveCredentialUseCase = mockSaveCredentialUseCase,
            logger = logger,
            loginStatus = LoginStatus
        )
        viewModel.login()

        viewModel.snackBarMessage.test {
            assertEquals(R.string.error_in_server, awaitItem().messageId)
            cancelAndIgnoreRemainingEvents()
        }
    }
}