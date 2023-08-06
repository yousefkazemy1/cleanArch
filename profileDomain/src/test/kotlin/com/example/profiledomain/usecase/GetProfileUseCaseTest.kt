package com.example.profiledomain.usecase

import com.example.core.model.Credential
import com.example.core.model.Result
import com.example.core.model.User
import com.example.core.utils.login.LoginStatus
import com.example.profiledomain.model.Profile
import com.example.profiledomain.repository.ProfileRespository
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GetProfileUseCaseTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @RelaxedMockK
    lateinit var repository: ProfileRespository

    @Test
    fun `user is not loggined, return ecception`() = runBlocking {
        val usecase = GetProfileUseCase(
            repository = repository, loginStatus = LoginStatus
        )

        val exception = assertFailsWith<RuntimeException> {
            usecase.invoke(userId = 1)
        }

        assertEquals("User hasn't logged in to get user information", exception.message)
    }

    @Test
    fun `user is loggined, return posts`() = runBlocking {
        val mockProfile = Profile(
            user = User(
                id = 1,
                userName = "username1",
                name = "name1",
            ), email = "email1@gmail.com", phone = "123456789"
        )
        coEvery { repository.getProfile(userId = 1) } returns Result.Success(mockProfile)

        val mockCredential = Credential(
            userId = 1,
            token = ('a'..'z').joinToString(""),
            refreshToken = ('a'..'x').joinToString(""),
            password = ('a'..'k').joinToString(""),
            name = ('a'..'f').joinToString("")
        )
        val loginStatus = LoginStatus
        loginStatus.checkIsUserLoggedIn(mockCredential)

        val usecase = GetProfileUseCase(
            repository = repository, loginStatus = loginStatus
        )
        val actualResult = usecase.invoke(userId = 1)

        Assert.assertEquals(Result.Success(mockProfile), actualResult)
    }
}