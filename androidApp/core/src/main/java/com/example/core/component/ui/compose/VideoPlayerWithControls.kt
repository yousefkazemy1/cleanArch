package com.example.core.component.ui.compose

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.core.R
import com.example.core.utils.density
import com.example.core.utils.toDp

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun VideoPlayerWithControls(
    exoPlayer: ExoPlayer?,
    scaledMediaSize: Pair<UShort, UShort>,
) {
    val context = LocalContext.current
    val playerView = remember {
        val layout = LayoutInflater.from(context).inflate(R.layout.video_player_auto, null)
        val playerView = (layout.findViewById(R.id.playerView) as PlayerView).apply {
            player = exoPlayer
            setShutterBackgroundColor(Color.TRANSPARENT)
        }
        layout.id = View.generateViewId()
        playerView
    }

    AndroidView(
        { playerView },
        Modifier
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
    )
}