package com.example.cleanarchproject.ui.home

import app.cash.turbine.test
import com.example.contentdatasource.repository.post.fake.PostRepositoryImplFake
import com.example.contentdomain.usecase.GetHomePostsUseCase
import com.example.core.model.Result
import com.example.core.model.mapToPostItemUIs
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class HomeViewModelTest {

    @Test
    fun `get home posts successfully`() = runBlocking {
        val useCase = mockk<GetHomePostsUseCase>()
        val mockPosts = PostRepositoryImplFake().getPosts()
        coEvery { useCase.invoke() } returns mockPosts

        val viewModel = HomeViewModel(useCase = useCase)
        viewModel.getHomePosts()

        viewModel.videos.test {
            Assert.assertEquals((mockPosts as Result.Success).data.mapToPostItemUIs(), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}