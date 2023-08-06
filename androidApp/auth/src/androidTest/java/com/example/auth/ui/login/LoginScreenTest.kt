package com.example.auth.ui.login

import android.content.Context
import androidx.compose.material.Text
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import com.example.auth.R
import com.example.auth.di.AppModule
import com.example.auth.ui.navigation.Screen
import com.example.auth.ui.signup.SignUpScreen
import com.example.auth.ui.signup.SignUpViewModel
import com.example.authdatasource.di.ApiModule
import com.example.authdatasource.repository.fake.AuthRepositoryImplFake
import com.example.authdomain.model.Auth
import com.example.authdomain.repository.AuthRepository
import com.example.authdomain.repository.CredentialRepository
import com.example.authdomain.usecase.LoginUseCase
import com.example.authdomain.usecase.SaveCredentialUseCase
import com.example.authdomain.usecase.SignUpUseCase
import com.example.core.component.ui.compose.navigation.BottomBarScreen
import com.example.core.model.Result
import com.example.core.utils.logger.Logger
import com.example.core.utils.login.LoginStatus
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class, ApiModule::class)
class LoginScreenTest {

    @get:Rule(order = 1)
    val composeRule = createComposeRule()

    lateinit var repository: AuthRepository
    lateinit var credentialRepository: CredentialRepository

    @Before
    fun setUp() {
        repository = AuthRepositoryImplFake()
        credentialRepository = mockk()

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
                startDestination = Screen.LoginScreen.route
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
                composable(route = BottomBarScreen.Home.route) {
                    Text(text = "home screen")
                }
            }
        }
    }

    @Test
    fun clickLoginButtonWithIncorrectFormatCredential_isSnackBarVisibleWithValidationError() {
        composeRule.onNodeWithTag("username_input")
            .performTextInput("samplegmail.com")
        composeRule.onNodeWithTag("password_input")
            .performTextInput("Aa1234!")

        composeRule.onNodeWithTag("login_button").performClick()

        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

        composeRule.onNodeWithText(context.getString(R.string.email_validation_error_message)).assertIsDisplayed()
    }

    @Test
    fun clickLoginButtonWithWrongCredential_isSnackBarVisibleWithServerError() {
        composeRule.onNodeWithTag("username_input")
            .performTextInput("sample@gmail.com")
        composeRule.onNodeWithTag("password_input")
            .performTextInput("Aa1234!")

        composeRule.onNodeWithTag("login_button").performClick()

        composeRule.onNodeWithText("username or password is wrong").assertIsDisplayed()
    }

    @Test
    fun clickLoginButtonWithCorrectCredential_navigateToHomeScreen() {
        runBlocking {
            val auth = Auth(email = "sample@gmail.com", password = "Aa1234!")
            repository.signUp(auth = auth)
            val loginResult = repository.login(auth = auth)
            if (loginResult is Result.Success) {
                every {
                    credentialRepository.saveCredential(credential = loginResult.data)
                } just Runs
            }
        }
        composeRule.onNodeWithTag("username_input")
            .performTextInput("sample@gmail.com")
        composeRule.onNodeWithTag("password_input")
            .performTextInput("Aa1234!")

        composeRule.onNodeWithTag("login_button").performClick()

        composeRule.onNodeWithText("home screen").assertIsDisplayed()
    }

    @Test
    fun clickSignUpButton_navigateToSignUpScreen() {
        composeRule.onNodeWithTag("sign_up_text_button").performClick()

        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        composeRule.onNodeWithText(context.getString(R.string.action_login_instead)).assertIsDisplayed()
    }
}