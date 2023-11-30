package com.example.cleanarchproject.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.NavController
import com.example.cleanarchproject.ui.home.HomeViewModel
import com.example.core.component.ui.compose.post.PostItemView
import com.example.core.model.PostItemUI
import com.example.core.utils.density
import com.example.core.utils.screenSize
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Math.abs

@Composable
fun HomeScreen(
    navController: NavController? = null,
    viewModel: HomeViewModel = hiltViewModel(),
    paddingBottom: UShort = 0u,
) {
    val posts by viewModel.videos.collectAsStateWithLifecycle()
    var playingVideoItem by remember {
        mutableStateOf(posts.firstOrNull())
    }
    var isPlayerReady by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current
    val lifeCycleOwner = LocalLifecycleOwner.current

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build()
    }

    val lazyColumnState = rememberLazyListState()

    val playingItemFlow = remember {
        snapshotFlow { lazyColumnState.playingItem(posts) }
    }

    SideEffect {
        viewModel.getHomePosts()
    }

    LaunchedEffect(Unit) {
        playingItemFlow.collect { videoItem ->
            if (playingVideoItem?.id != videoItem?.id) {
                exoPlayer.setMediaItem(MediaItem.fromUri(""))
                playingVideoItem = videoItem
            }
        }
    }

    LaunchedEffect(key1 = playingVideoItem) {
        exoPlayer.setMediaItem(MediaItem.fromUri(playingVideoItem?.mediaUI?.video ?: ""))
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
    }

    DisposableEffect(key1 = exoPlayer) {
        val exoPlayerListener = object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                when (playbackState) {
                    Player.STATE_READY -> {
                        isPlayerReady = true
                    }
                    Player.STATE_BUFFERING -> {
                        isPlayerReady = false
                    }
                }
            }
            override fun onPlayerError(error: PlaybackException) {
                super.onPlayerError(error)
                println("onPlayerError: " + error.message)
            }
        }
        exoPlayer.addListener(exoPlayerListener)

        val lifeCycleObserver = LifecycleEventObserver { source, event ->
            when (event) {
                Lifecycle.Event.ON_START -> exoPlayer.play()
                Lifecycle.Event.ON_STOP -> exoPlayer.pause()
                else -> {}
            }
        }
        lifeCycleOwner.lifecycle.addObserver(lifeCycleObserver)

        onDispose {
            exoPlayer.removeListener(exoPlayerListener)
            exoPlayer.release()

            lifeCycleOwner.lifecycle.removeObserver(lifeCycleObserver)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("lazyColumn"),
        contentPadding = WindowInsets.systemBars.only(WindowInsetsSides.Vertical)
            .add(WindowInsets(bottom = 8.dp)).asPaddingValues(),
        state = lazyColumnState
    ) {
        items(posts) { post ->
            val shouldPlay = post.id == playingVideoItem?.id
            PostItemView(
                post = post,
                screenSize = Pair(screenSize.x.toUShort(), screenSize.y.toUShort()),
                density = density,
                isPlaying = shouldPlay,
                exoPlayer = if (shouldPlay) exoPlayer else null,
                extraHeight = paddingBottom,
                onLikeClick = {},
                onShareClick = {},
                isPlayerReady = isPlayerReady
            )
        }
    }
}

private fun LazyListState.playingItem(videos: List<PostItemUI>): PostItemUI? {
    if (layoutInfo.visibleItemsInfo.isEmpty() || videos.isEmpty()) return null
    val layoutInfo = layoutInfo
    val visibleItems = layoutInfo.visibleItemsInfo
    val lastItem = visibleItems.last()
    val firstItemVisible = firstVisibleItemIndex == 0 && firstVisibleItemScrollOffset == 0
    val itemSize = lastItem.size
    val itemOffset = lastItem.offset
    val totalOffset = layoutInfo.viewportEndOffset
    val lastItemVisible = lastItem.index == videos.size - 1 && totalOffset - itemOffset >= itemSize
    val midPoint = (layoutInfo.viewportStartOffset + layoutInfo.viewportEndOffset) / 2
    val centerItems = visibleItems.sortedBy { abs(it.offset + it.size / 2 - midPoint) }

    val postItemUI = when {
        firstItemVisible -> videos.first()
        lastItemVisible -> videos.last()
        else -> centerItems.firstNotNullOf { videos[it.index] }
    }

    if (!postItemUI.mediaUI.video.isNullOrEmpty()) {
        return postItemUI
    }

    return null
}

