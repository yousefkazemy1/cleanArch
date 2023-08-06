package com.example.authdatasource.repository

import com.example.authdatasource.Utils
import com.example.authdatasource.remote.AuthApi
import com.example.authdatasource.remote.response.LoginResponse
import com.example.authdatasource.remote.response.SignUpResponse
import com.example.authdomain.model.Auth
import com.example.authdomain.repository.AuthRepository
import com.example.core.model.ErrorCause
import com.example.core.model.Result
import com.example.core.utils.logger.ConsoleLogger
import com.example.data.utils.convertToErrorResponse
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthRepositoryImplTest {

    lateinit var mockWebServer: MockWebServer
    lateinit var authRepository: AuthRepository

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        val authApi = Retrofit.Builder().baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create()).build().create(AuthApi::class.java)
        val logger = Mockito.spy(ConsoleLogger())
        Mockito.doNothing().`when`(logger).log()
        authRepository = AuthRepositoryImpl(authApi = authApi, logger = logger)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `login with wrong credential, login error`() = runBlocking {
        val mockResponse = MockResponse()
        val content = Utils.readFileResourse("/error_login_response.json")
        mockResponse.setResponseCode(422)
        mockResponse.setBody(content)
        mockWebServer.enqueue(mockResponse)

        val request = Auth(email = "", password = "")

        val response = authRepository.login(request)

        val expectedResult = content.convertToErrorResponse()

        val result =
            response is Result.Error && response.error.errorCause == ErrorCause.API && response.error.errorResponse == expectedResult.message

        assertEquals(true, result)
    }

    @Test
    fun `login with correct credential, login success`() = runBlocking {
        val mockResponse = MockResponse()
        val content = Utils.readFileResourse("/success_login_response.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(content)
        mockWebServer.enqueue(mockResponse)

        val request = Auth(email = "", password = "")
        val response = authRepository.login(request)

        val expectedResult = Gson().fromJson<LoginResponse>(content, LoginResponse::class.java)

        val result =
            response is Result.Success && response.data.userId == expectedResult.userId && response.data.token == expectedResult.token && response.data.refreshToken == expectedResult.refreshToken

        assertEquals(true, result)
    }

    @Test
    fun `login with server error response`() = runBlocking {
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(500)
        mockWebServer.enqueue(mockResponse)

        val request = Auth(email = "", password = "")
        val response = authRepository.login(request)

        val result =
            response is Result.Error && response.error.errorCause == ErrorCause.API && response.error.errorCode == 500

        assertEquals(true, result)
    }

    @Test
    fun `login with timeout exception`() = runBlocking {
        mockWebServer.enqueue(MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AT_START))

        val request = Auth(email = "", password = "")
        val response = authRepository.login(request)

        val result = response is Result.Error && response.error.errorCause == ErrorCause.EXCEPTION

        assertEquals(true, result)
    }

    @Test
    fun `sign up with invalid information, sign up error`() = runBlocking {
        val mockResponse = MockResponse()
        val content = Utils.readFileResourse("/error_signup_response.json")
        mockResponse.setResponseCode(422)
        mockResponse.setBody(content)
        mockWebServer.enqueue(mockResponse)

        val request = Auth(email = "", password = "")

        val response = authRepository.signUp(request)

        val expectedResult = content.convertToErrorResponse()

        val result =
            response is Result.Error && response.error.errorCause == ErrorCause.API && response.error.errorResponse == expectedResult.message

        assertEquals(true, result)
    }

    @Test
    fun `sign up with correct information, sign up success`() = runBlocking {
        val mockResponse = MockResponse()
        val content = Utils.readFileResourse("/success_login_response.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(content)
        mockWebServer.enqueue(mockResponse)

        val request = Auth(email = "", password = "")
        val response = authRepository.signUp(request)

        val expectedResult = Gson().fromJson<SignUpResponse>(content, SignUpResponse::class.java)

        val result =
            response is Result.Success && response.data.userId == expectedResult.userId && response.data.token == expectedResult.token && response.data.refreshToken == expectedResult.refreshToken

        assertEquals(true, result)
    }

    @Test
    fun `sign up with server error response`() = runBlocking {
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(500)
        mockWebServer.enqueue(mockResponse)

        val request = Auth(email = "", password = "")
        val response = authRepository.signUp(request)

        val result =
            response is Result.Error && response.error.errorCause == ErrorCause.API && response.error.errorCode == 500

        assertEquals(true, result)
    }

    @Test
    fun `sign up with timeout exception`() = runBlocking {
        mockWebServer.enqueue(MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AT_START))

        val request = Auth(email = "", password = "")
        val response = authRepository.signUp(request)

        val result = response is Result.Error && response.error.errorCause == ErrorCause.EXCEPTION

        assertEquals(true, result)
    }
}