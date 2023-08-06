package com.example.contentdomain.usecase

import com.example.contentdomain.repository.PostRepository
import com.example.core.model.Credential
import com.example.core.model.Post
import com.example.core.model.Result
import com.example.core.utils.login.LoginStatus
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GetHomePostsUseCaseTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @RelaxedMockK
    lateinit var repository: PostRepository

    @Test
    fun `user is not loggined, return ecception`() = runBlocking {
        val usecase = GetHomePostsUseCase(
            repository = repository,
            loginStatus = LoginStatus
        )

        val exception = assertFailsWith<RuntimeException> {
            usecase.invoke()
        }

        assertEquals("User hasn't logged in to get home posts", exception.message)
    }

    @Test
    fun `user is loggined, return posts`() = runBlocking {
        coEvery { repository.getPosts() } returns Result.Success(emptyList<Post>())

        val mockCredential = Credential(
            userId = 1,
            token = ('a'..'z').joinToString(""),
            refreshToken = ('a'..'x').joinToString(""),
            password = ('a'..'k').joinToString(""),
            name = ('a'..'f').joinToString("")
        )
        val loginStatus = LoginStatus
        loginStatus.checkIsUserLoggedIn(mockCredential)

        val usecase = GetHomePostsUseCase(
            repository = repository,
            loginStatus = loginStatus
        )
        val actualResult = usecase.invoke()

        Assert.assertEquals(Result.Success(emptyList<Post>()), actualResult)
    }
}