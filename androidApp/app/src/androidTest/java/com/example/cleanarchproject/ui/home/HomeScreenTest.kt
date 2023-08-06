package com.example.cleanarchproject.ui.home

import android.graphics.Point
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.cleanarchproject.ui.HomeScreen
import com.example.contentdatasource.repository.post.fake.PostRepositoryImplFake
import com.example.contentdomain.usecase.GetHomePostsUseCase
import com.example.core.model.PostItemUI
import com.example.core.model.Result
import com.example.core.model.mapToPostItemUIs
import com.example.core.utils.density
import com.example.core.utils.screenSize
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule(order = 1)
    val composeRule = createComposeRule()

    lateinit var mockPostLists: List<PostItemUI>

    @Before
    fun setUp() = runBlocking {
        val mockUseCase = mockk<GetHomePostsUseCase>()
        val viewModel = HomeViewModel(useCase = mockUseCase)
        val mockPosts = PostRepositoryImplFake().getPosts()
        mockPostLists = (mockPosts as Result.Success).data.mapToPostItemUIs()
        coEvery { mockUseCase.invoke() } returns mockPosts

        composeRule.setContent {
            screenSize = Point(1080, 2177)
            density = 2f
            HomeScreen(
                viewModel = viewModel
            )
        }
    }

    @Test
    fun scrollToDifferentPositions_showCorrectData() {
        composeRule.onNodeWithTag("lazyColumn")
            .performScrollToNode(hasText(mockPostLists.first().user.username))

        composeRule.onNodeWithTag("lazyColumn")
            .performScrollToNode(hasText(mockPostLists.last().user.username))
    }
}