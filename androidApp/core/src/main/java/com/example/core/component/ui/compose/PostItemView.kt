package com.example.core.component.ui.compose

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import com.example.core.R
import com.example.core.model.MediaUI
import com.example.core.model.PostItemUI
import com.example.core.model.UserUI
import com.example.core.utils.density
import com.example.core.utils.getScaledMediaSize
import com.example.core.utils.toDp

@Preview
@Composable
fun PostPreview() {
    PostItemView(modifier = Modifier.fillMaxSize(),
        post = PostItemUI(
            id = 1, user = UserUI(
                id = 1, username = "username1"
            ), title = "This is test title", mediaUI = MediaUI(
                image = "https://www.pakainfo.com/wp-content/uploads/2021/09/sample-image-url-for-testing-300x169.jpg",
                width = 291u,
                height = 163u
            )
        ),
        screenSize = Pair(1080u, 2177u),
        density = 2f,
        isPlaying = false,
        exoPlayer = null,
        extraHeight = 0u,
        {

        },
        {

        })
}
val DrawableId = SemanticsPropertyKey<Int>("DrawableResId")
var SemanticsPropertyReceiver.drawableId by DrawableId
val drawableFavorite = R.drawable.favorite_24
val drawableUnFavorite = R.drawable.favorite_outline_24

@Composable
fun PostItemView(
    modifier: Modifier = Modifier,
    post: PostItemUI,
    screenSize: Pair<UShort, UShort>,
    density: Float,
    isPlaying: Boolean = false,
    exoPlayer: ExoPlayer? = null,
    extraHeight: UShort = 0u,
    onLikeClick: (isLike: Boolean) -> Unit,
    onShareClick: () -> Unit,
) {
    var isLiked by remember { mutableStateOf(false) }

    Column(modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, top = 4.dp, end = 12.dp, bottom = 4.dp)
        ) {
            RoundedAsyncImageView(
                modifier = Modifier
                    .padding(end = 6.dp)
                    .size(38.dp)
                    .testTag("user_image"),
                imageUrl = post.user.profileImage,
                scale = ContentScale.Crop
            )

            Text(
                modifier = Modifier.testTag("username_text"),
                text = post.user.username,
                fontSize = 18.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            val scaledMediaSize = getScaledMediaSize(
                screenSize = screenSize,
                mediaSize = Pair(post.mediaUI.width, post.mediaUI.height),
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
                model = post.mediaUI.image,
                contentDescription = "image",
                contentScale = ContentScale.Crop
            )

            if (isPlaying) {
                VideoPlayerWithControls(
                    exoPlayer = exoPlayer, scaledMediaSize = scaledMediaSize
                )
            }
        }

        Row(Modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp)) {
            IconButton(
                modifier = Modifier
                    .padding(4.dp)
                    .testTag("like_button"),
                onClick = {
                    isLiked = !isLiked
                },
            ) {
                Icon(
                    modifier = Modifier
                        .testTag("like_icon")
                        .semantics {
                            drawableId = if (isLiked) {
                                drawableFavorite
                            } else {
                                drawableUnFavorite
                            }
                        },
                    imageVector = if (isLiked) {
                        ImageVector.vectorResource(id = drawableFavorite)
                    } else {
                        ImageVector.vectorResource(id = drawableUnFavorite)
                    }, contentDescription = "Like", tint = if (isLiked) {
                        Color.Red
                    } else {
                        Color.Gray
                    }
                )
            }

            IconButton(
                modifier = Modifier.padding(4.dp),
                onClick = {

                },
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share",
                    tint = Color.Gray
                )
            }
        }
    }
}

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun VideoPlayerWithControls(exoPlayer: ExoPlayer?, scaledMediaSize: Pair<UShort, UShort>) {
    val context = LocalContext.current
    val playerView = remember {
        val layout = LayoutInflater.from(context).inflate(R.layout.video_player_auto, null)
        val playerView = (layout.findViewById(R.id.playerView) as PlayerView).apply {
            player = exoPlayer
            setShutterBackgroundColor(android.graphics.Color.TRANSPARENT)
        }
        layout.findViewById<ImageButton>(R.id.exo_pause).setOnClickListener { exoPlayer?.pause() }
        layout.findViewById<ImageButton>(R.id.exo_play).setOnClickListener { exoPlayer?.play() }
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
            .background(Color.Black)
    )
}