package com.example.authdatasource.remote

import com.example.authdatasource.Utils
import com.example.authdatasource.remote.request.LoginRequest
import com.example.authdatasource.remote.request.SignUpRequest
import com.example.authdatasource.remote.response.LoginResponse
import com.example.authdatasource.remote.response.SignUpResponse
import com.example.data.utils.convertToErrorResponse
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class AuthApiTest {
    lateinit var mockWebServer: MockWebServer
    lateinit var authApi: AuthApi

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        authApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }

    @After
    fun shotDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `request with wrong credential, login error`() = runBlocking {
        val mockResponse = MockResponse()
        val content = Utils.readFileResourse("/error_login_response.json")
        mockResponse.setResponseCode(422)
        mockResponse.setBody(content)
        mockWebServer.enqueue(mockResponse)

        val request = LoginRequest(username = "", password = "")
        val response = authApi.login(request)

        val expectedResult = content.convertToErrorResponse()
        val errorResult = response.errorBody()!!.string().convertToErrorResponse()

        assertEquals(expectedResult, errorResult)
    }

    @Test
    fun `request with correct credential, login success`() = runBlocking {
        val mockResponse = MockResponse()
        val content = Utils.readFileResourse("/success_login_response.json")
        mockResponse.setBody(content)
        mockResponse.setResponseCode(200)
        mockWebServer.enqueue(mockResponse)

        val request = LoginRequest(username = "", password = "")
        val response = authApi.login(request)

        val expectedResult = Gson().fromJson<LoginResponse>(content, LoginResponse::class.java)
        val succesResult = response.body()!!

        assertEquals(expectedResult, succesResult)
    }

    @Test
    fun `request with wrong credential, sign up error`() = runBlocking {
        val mockResponse = MockResponse()
        val content = Utils.readFileResourse("/error_signup_response.json")
        mockResponse.setResponseCode(422)
        mockResponse.setBody(content)
        mockWebServer.enqueue(mockResponse)

        val request = SignUpRequest(username = "", password = "")
        val response = authApi.signUp(request)

        val expectedResult = content.convertToErrorResponse()
        val errorResult = response.errorBody()!!.string().convertToErrorResponse()

        assertEquals(expectedResult, errorResult)
    }

    @Test
    fun `request with correct credential, sign up success`() = runBlocking {
        val mockResponse = MockResponse()
        val content = Utils.readFileResourse("/success_login_response.json")
        mockResponse.setBody(content)
        mockResponse.setResponseCode(200)
        mockWebServer.enqueue(mockResponse)

        val request = SignUpRequest(username = "", password = "")
        val response = authApi.signUp(request)

        val expectedResult = Gson().fromJson<SignUpResponse>(content, SignUpResponse::class.java)
        val succesResult = response.body()!!

        assertEquals(expectedResult, succesResult)
    }
}