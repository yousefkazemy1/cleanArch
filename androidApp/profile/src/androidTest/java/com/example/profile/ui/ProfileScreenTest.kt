package com.example.profile.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.core.model.Credential
import com.example.core.model.Result
import com.example.core.utils.login.LoginStatus
import com.example.profile.model.ProfileUI
import com.example.profile.model.convertToProfileUI
import com.example.profiledatasource.repository.fake.ProfileRepositoryImplFake
import com.example.profiledomain.usecase.GetProfileUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProfileScreenTest {

    @get:Rule(order = 1)
    val composeRule = createComposeRule()

    lateinit var mockProfileData: ProfileUI


    @Before
    fun setUp() = runBlocking {
        val useCase = mockk<GetProfileUseCase>()
        val mockProfile = ProfileRepositoryImplFake().getProfile(1)
        mockProfileData = (mockProfile as Result.Success).data.convertToProfileUI()
        coEvery { useCase.invoke(1) } returns mockProfile

        val mockCredential = Credential(
            userId = 1,
            token = ('a'..'z').joinToString(""),
            refreshToken = ('a'..'x').joinToString(""),
            password = ('a'..'k').joinToString(""),
            name = ('a'..'f').joinToString("")
        )
        val loginStatus = LoginStatus
        loginStatus.checkIsUserLoggedIn(credential = mockCredential)

        val viewModel = ProfileViewModel(
            useCase = useCase,
            loginStatus = loginStatus
        )
        composeRule.setContent {
            ProfileScreen(
                viewModel = viewModel
            )
        }
    }

    @Test
    fun assertProfileDataIsDisplayed() {
        composeRule.onNodeWithText(mockProfileData.user.name!!).assertIsDisplayed()
        composeRule.onNodeWithText(mockProfileData.user.username).assertIsDisplayed()
        composeRule.onNodeWithText(mockProfileData.email!!).assertIsDisplayed()
        composeRule.onNodeWithText(mockProfileData.phone!!).assertIsDisplayed()
    }
}