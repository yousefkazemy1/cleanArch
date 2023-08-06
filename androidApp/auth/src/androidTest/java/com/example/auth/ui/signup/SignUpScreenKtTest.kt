package com.example.auth.ui.signup

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import com.example.auth.R
import com.example.auth.ui.login.LoginScreen
import com.example.auth.ui.login.LoginViewModel
import com.example.auth.ui.navigation.Screen
import com.example.authdatasource.repository.fake.AuthRepositoryImplFake
import com.example.authdomain.repository.AuthRepository
import com.example.authdomain.repository.CredentialRepository
import com.example.authdomain.usecase.LoginUseCase
import com.example.authdomain.usecase.SaveCredentialUseCase
import com.example.authdomain.usecase.SignUpUseCase
import com.example.core.utils.logger.Logger
import com.example.core.utils.login.LoginStatus
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SignUpScreenKtTest {

    @get:Rule(order = 1)
    val composeRule = createComposeRule()

    @Before
    fun setUp() {
        val repository: AuthRepository = AuthRepositoryImplFake()
        val credentialRepository: CredentialRepository = mockk()

        val logger: Logger = object : Logger {
            override fun log(vararg message: String) {
                println()
            }
        }
        val loginUseCase = LoginUseCase(
            repository = repository,
            logger = logger
        )
        val signupUseCase = SignUpUseCase(
            repository = repository,
            logger = logger
        )
        val saveCredentialUseCase = SaveCredentialUseCase(
            repository = credentialRepository
        )
        composeRule.setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = Screen.SignUpScreen.route
            ) {
                composable(route = Screen.LoginScreen.route) {
                    LoginScreen(
                        navController = navController,
                        viewModel = LoginViewModel(
                            useCase = loginUseCase,
                            saveCredentialUseCase = saveCredentialUseCase,
                            logger = logger,
                            loginStatus = LoginStatus
                        )
                    )
                }
                composable(route = Screen.SignUpScreen.route) {
                    SignUpScreen(
                        navController = navController,
                        viewModel = SignUpViewModel(
                            signUpUseCase = signupUseCase,
                            logger = logger
                        )
                    )
                }
            }
        }
    }

    @Test
    fun clickSignUpButtonWithIncorrectFormatCredential_isSnackBarVisibleWithValidationError() {
        composeRule.onNodeWithTag("username_input")
            .performTextInput("samplegmail.com")
        composeRule.onNodeWithTag("password_input")
            .performTextInput("Aa1234!")

        composeRule.onNodeWithTag("sign_up_button").performClick()

        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

        composeRule.onNodeWithText(context.getString(R.string.email_validation_error_message)).assertIsDisplayed()
    }

    @Test
    fun clickSignUpButtonWithCorrectCredential_isSnackBarVisibleWithSuccessfullSignUpMessage() {
        composeRule.onNodeWithTag("username_input")
            .performTextInput("sample@gmail.com")
        composeRule.onNodeWithTag("password_input")
            .performTextInput("Aa1234!")
        composeRule.onNodeWithTag("name_input").performTextInput("sample")

        composeRule.onNodeWithTag("sign_up_button").performClick()

        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

        composeRule.onNodeWithText(context.getString(R.string.signed_up_successfully)).assertIsDisplayed()
    }

    @Test
    fun clickLoginButton_navigateToLoginScreen() {
        composeRule.onNodeWithTag("login_text_button").performClick()

        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        composeRule.onNodeWithText(context.getString(R.string.action_signup_instead)).assertIsDisplayed()
    }
}