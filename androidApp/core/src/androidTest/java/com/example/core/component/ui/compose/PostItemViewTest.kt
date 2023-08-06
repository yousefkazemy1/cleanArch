package com.example.core.component.ui.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import com.example.core.R
import com.example.core.model.MediaUI
import com.example.core.model.PostItemUI
import com.example.core.model.UserUI
import com.example.core.utils.toDp
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PostItemViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    val mockPost = PostItemUI(
        id = 1,
        user = UserUI(
            id = 1, username = "username1"
        ),
        title = "This is test title",
        mediaUI = MediaUI(
            image = "https://www.pakainfo.com/wp-content/uploads/2021/09/sample-image-url-for-testing-300x169.jpg",
            width = 291u,
            height = 163u
        )
    )

    val mockScreenSize = Pair<UShort, UShort>(1080u, 2177u)

    @Before
    fun setUp() {
        composeTestRule.setContent {
            PostItemView(modifier = Modifier.fillMaxSize(),
                post = mockPost,
                screenSize = mockScreenSize,
                density = 2f,
                onLikeClick = {

                },
                onShareClick = {

                }
            )
        }
    }

    @Test
    fun assertUsernameTextView() {
        val userNameText = composeTestRule.onNodeWithTag("username_text")
        userNameText.assertTextEquals(mockPost.user.username)
        userNameText.assertTextColor(Color.Black)
    }

    @Test
    fun assertMediaImageSize() {
        val mediaImage = composeTestRule.onNodeWithTag("media_image")
        val mediaHeight = Math.round(
            (mockScreenSize.first.toDouble() * mockPost.mediaUI.height.toDouble()) / mockPost.mediaUI.width.toDouble()
        ).toInt().toDp(2f).dp
        mediaImage.assertHeightIsEqualTo(mediaHeight)
    }

    @Test
    fun clickLikeButton_assertLikeAndUnLikeDrawable() {
        composeTestRule.onNodeWithTag("like_button").performClick()
        composeTestRule.onNode(hasDrawable(R.drawable.favorite_24)).assertIsDisplayed()

        composeTestRule.onNodeWithTag("like_button").performClick()
        composeTestRule.onNode(hasDrawable(R.drawable.favorite_outline_24)).assertIsDisplayed()
    }
}