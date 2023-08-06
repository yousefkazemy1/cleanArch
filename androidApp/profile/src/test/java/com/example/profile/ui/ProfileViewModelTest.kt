package com.example.profile.ui

import app.cash.turbine.test
import com.example.core.model.Credential
import com.example.core.model.Result
import com.example.core.utils.login.LoginStatus
import com.example.profile.model.convertToProfileUI
import com.example.profiledatasource.repository.fake.ProfileRepositoryImplFake
import com.example.profiledomain.usecase.GetProfileUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class ProfileViewModelTest {

    @Test
    fun `get profile info successfully`() = runBlocking {
        val useCase = mockk<GetProfileUseCase>()
        val mockProfile = ProfileRepositoryImplFake().getProfile(1)
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
            useCase = useCase, loginStatus = loginStatus
        )
        viewModel.getProfileInfo()

        viewModel.profileState.test {
            Assert.assertEquals(
                (mockProfile as Result.Success).data.convertToProfileUI(), awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }
}