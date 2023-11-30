package com.example.core.component.ui.compose.post

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.media3.exoplayer.ExoPlayer
import coil.compose.AsyncImage
import com.example.core.component.ui.compose.VideoPlayerWithControls
import com.example.core.model.MediaUI
import com.example.core.utils.getScaledMediaSize
import com.example.core.utils.toDp

@Composable
fun PostMedia(
    modifier: Modifier = Modifier,
    mediaList: List<MediaUI>,
    density: Float,
    screenSize: Pair<UShort, UShort>,
    extraHeight: UShort = 0u,
    isPlaying: Boolean = false,
    isPlayerReady: Boolean = false,
    exoPlayer: ExoPlayer? = null,
) {
    if (mediaList.size == 1) {
        val mediaUI = mediaList[0]
        Box(
            modifier = modifier.fillMaxWidth()
        ) {
            val scaledMediaSize = getScaledMediaSize(
                screenSize = screenSize,
                mediaSize = Pair(mediaUI.width, mediaUI.height),
                extraHeight = extraHeight
            )
            AsyncImage(
                modifier = Modifier
                    .width(
                        width = scaledMediaSize.first
                            .toDp(density)
                            .toInt().dp
                    )
                    .height(
                        height = scaledMediaSize.second
                            .toDp(density)
                            .toInt().dp
                    )
                    .align(Alignment.Center)
                    .testTag("media_image"),
                model = mediaUI.image,
                contentDescription = "image",
                contentScale = ContentScale.Crop
            )

            if (isPlayerReady && isPlaying) {
                VideoPlayerWithControls(
                    exoPlayer = exoPlayer, scaledMediaSize = scaledMediaSize
                )
            }

            if (!isPlayerReady && isPlaying) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(32.dp)
                        .align(Alignment.Center),
                    color = Color.Red,
                )
            }
        }
    }
}