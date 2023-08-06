package com.example.contentdatasource.repository

import com.example.contentdatasource.Utils
import com.example.contentdatasource.remote.PostApi
import com.example.contentdomain.repository.PostRepository
import com.example.core.model.ErrorCause
import com.example.core.model.Result
import com.google.gson.GsonBuilder
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostRepositoryImplTest {

    lateinit var mockWebServer: MockWebServer
    lateinit var repository: PostRepository

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        val postApi = Retrofit.Builder().baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build().create(PostApi::class.java)
        repository = PostRepositoryImpl(postApi = postApi)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `get home posts successfully`() = runBlocking {
        val mockResponse = MockResponse()
        val content = Utils.readFileResourse("/home_posts.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(content)
        mockWebServer.enqueue(mockResponse)

        val response = repository.getPosts()

        val expectedResult = response is Result.Success && response.data.size > 0
        Assert.assertEquals(expectedResult, true)
    }

    @Test
    fun `get home posts with server error response`() = runBlocking {
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(500)
        mockWebServer.enqueue(mockResponse)

        val response = repository.getPosts()

        val expectedResult = response is Result.Error &&
                response.error.errorCause == ErrorCause.API &&
                response.error.errorCode == 500

        Assert.assertEquals(expectedResult, true)
    }

    @Test
    fun `get home posts with timeout exception`() = runBlocking {
        mockWebServer.enqueue(MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AT_START))

        val response = repository.getPosts()

        val expectedResult = response is Result.Error && response.error.errorCause == ErrorCause.EXCEPTION

        Assert.assertEquals(expectedResult, true)
    }
}