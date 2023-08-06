package com.example.profiledatasource.repository

import com.example.core.model.ErrorCause
import com.example.core.model.Result
import com.example.profiledatasource.Utils
import com.example.profiledatasource.remote.ProfileApi
import com.example.profiledatasource.remote.response.ProfileResponse
import com.example.profiledatasource.remote.response.mapToProfile
import com.example.profiledomain.repository.ProfileRespository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileRepositoryImplTest {

    lateinit var mockWebServer: MockWebServer
    lateinit var repository: ProfileRespository

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        val profileApi = Retrofit.Builder().baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build().create(ProfileApi::class.java)
        repository = ProfileRepositoryImpl(profileApi = profileApi)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `get profile successfully`() = runBlocking {
        val mockResponse = MockResponse()
        val content = Utils.readFileResourse("/profile.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(content)
        mockWebServer.enqueue(mockResponse)

        val response = repository.getProfile(userId = 1)

        var expectedResult = Gson().fromJson<ProfileResponse>(content, ProfileResponse::class.java)
            .mapToProfile()

        val finalExpectedResult = response is Result.Success && expectedResult == response.data

        Assert.assertEquals(finalExpectedResult, true)
    }

    @Test
    fun `get profile with server error response`() = runBlocking {
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(500)
        mockWebServer.enqueue(mockResponse)

        val response = repository.getProfile(userId = 1)

        val expectedResult =
            response is Result.Error && response.error.errorCause == ErrorCause.API && response.error.errorCode == 500

        assertEquals(expectedResult, true)
    }


    @Test
    fun `get profile with timeout exception`() = runBlocking {
        mockWebServer.enqueue(MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AT_START))

        val response = repository.getProfile(userId = 1)

        val expectedResult =  response is Result.Error && response.error.errorCause == ErrorCause.EXCEPTION

        assertEquals(expectedResult, true)
    }
}