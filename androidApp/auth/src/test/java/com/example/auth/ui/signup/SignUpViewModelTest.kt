package com.example.auth.ui.signup

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.auth.MainDispatcherRule
import com.example.auth.R
import com.example.authdomain.model.Auth
import com.example.authdomain.model.ValidationError
import com.example.authdomain.usecase.SignUpUseCase
import com.example.core.model.ErrorCause
import com.example.core.model.ErrorResult
import com.example.core.model.Result
import com.example.core.model.SuccessResult
import com.example.core.utils.logger.ConsoleLogger
import com.example.core.utils.logger.Logger
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class SignUpViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    lateinit var logger: Logger

    @Before
    fun setUp() {
        logger = Mockito.spy(ConsoleLogger())
        Mockito.doNothing().`when`(logger).log()
    }

    @Test
    fun `sign up with correct credential, set snackbar message successfully`() = runBlocking {
        val signUpUseCase = Mockito.mock(SignUpUseCase::class.java)
        Mockito.`when`(
            signUpUseCase.invoke(
                auth = Auth(email = "", password = "", name = "")
            )
        ).thenReturn(
            Result.Success(
                SuccessResult()
            )
        )

        val viewModel = SignUpViewModel(signUpUseCase = signUpUseCase, logger = logger)

        viewModel.register()

        viewModel.snackBarMessage.test {
            assertEquals(R.string.signed_up_successfully, awaitItem().messageId)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `sign up with invalid email, set snackbar message with email validation error`() = runBlocking {
        val signUpUseCase = Mockito.mock(SignUpUseCase::class.java)
        Mockito.`when`(
            signUpUseCase.invoke(
                auth = Auth(email = "", password = "", name = "")
            )
        ).thenReturn(
            Result.Error(
                ErrorResult(
                    errorCause = ErrorCause.VALIDATION,
                    errorResponse =  ValidationError.EMAIL.toString()
                )
            )
        )

        val viewModel = SignUpViewModel(signUpUseCase = signUpUseCase, logger = logger)

        viewModel.register()

        viewModel.snackBarMessage.test {
            assertEquals(R.string.email_validation_error_message, awaitItem().messageId)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `sign up with invalid password, set snackbar message with password validation error`() = runBlocking {
        val signUpUseCase = Mockito.mock(SignUpUseCase::class.java)
        Mockito.`when`(
            signUpUseCase.invoke(
                auth = Auth(email = "", password = "", name = "")
            )
        ).thenReturn(
            Result.Error(
                ErrorResult(
                    errorCause = ErrorCause.VALIDATION,
                    errorResponse =  ValidationError.PASSWORD.toString()
                )
            )
        )

        val viewModel = SignUpViewModel(signUpUseCase = signUpUseCase, logger = logger)

        viewModel.register()

        viewModel.snackBarMessage.test {
            assertEquals(R.string.password_validation_error_message, awaitItem().messageId)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `sign up with wrong credential, set snackbar message with api message error`() = runBlocking {
        val apiMessageError = "credntial.error"
        val signUpUseCase = Mockito.mock(SignUpUseCase::class.java)
        Mockito.`when`(
            signUpUseCase.invoke(
                auth = Auth(email = "", password = "", name = "")
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

        val viewModel = SignUpViewModel(signUpUseCase = signUpUseCase, logger = logger)

        viewModel.register()

        viewModel.snackBarMessage.test {
            assertEquals(apiMessageError, awaitItem().message)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `sign up and get server error, set snackbar message with error message`() = runBlocking {
        val signUpUseCase = Mockito.mock(SignUpUseCase::class.java)
        Mockito.`when`(
            signUpUseCase.invoke(
                auth = Auth(email = "", password = "", name = "")
            )
        ).thenReturn(
            Result.Error(
                ErrorResult(
                    errorCause = ErrorCause.API,
                    errorCode =  500
                )
            )
        )

        val viewModel = SignUpViewModel(signUpUseCase = signUpUseCase, logger = logger)

        viewModel.register()

        viewModel.snackBarMessage.test {
            assertEquals(R.string.error_in_server, awaitItem().messageId)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `sign up get unknown exception, set snackbar message with error message`() = runBlocking {
        val signUpUseCase = Mockito.mock(SignUpUseCase::class.java)
        Mockito.`when`(
            signUpUseCase.invoke(
                auth = Auth(email = "", password = "", name = "")
            )
        ).thenReturn(
            Result.Error(
                ErrorResult(
                    errorCause = ErrorCause.EXCEPTION
                )
            )
        )

        val viewModel = SignUpViewModel(signUpUseCase = signUpUseCase, logger = logger)

        viewModel.register()

        viewModel.snackBarMessage.test {
            assertEquals(R.string.error_in_server, awaitItem().messageId)
            cancelAndIgnoreRemainingEvents()
        }
    }
}