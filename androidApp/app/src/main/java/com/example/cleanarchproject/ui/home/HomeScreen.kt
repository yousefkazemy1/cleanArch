package com.example.cleanarchproject.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.cleanarchproject.ui.home.HomeViewModel
import com.example.core.component.ui.compose.PostItemView
import com.example.core.utils.density
import com.example.core.utils.screenSize

@Composable
fun HomeScreen(
    navController: NavController? = null,
    viewModel: HomeViewModel = hiltViewModel(),
    paddingBottom: UShort = 0u,
) {
    viewModel.getHomePosts()

    val posts by viewModel.videos.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("lazyColumn"),
        contentPadding = WindowInsets.systemBars.only(WindowInsetsSides.Vertical)
            .add(WindowInsets(bottom = 8.dp)).asPaddingValues()
    ) {
        items(posts) { post ->
            PostItemView(
                post = post,
                screenSize = Pair(screenSize.x.toUShort(), screenSize.y.toUShort()),
                density = density,
                extraHeight = paddingBottom,
                onLikeClick = {},
                onShareClick = {},
            )
        }
    }
}

