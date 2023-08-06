package com.example.core.component.ui.compose

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.core.R

@Preview
@Composable
fun RoundedAsyncImageViewPreview() {
    RoundedAsyncImageView(
        imageUrl = ""
    )
}

@Composable
fun RoundedAsyncImageView(
    modifier: Modifier = Modifier,
    imageUrl: String ? = null,
    contentDescription: String = "user image",
    scale: ContentScale = ContentScale.Fit
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        placeholder = painterResource(id = R.drawable.baseline_person_24),
        error = painterResource(id = R.drawable.baseline_person_24),
        modifier = modifier
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .border(width = 1.dp, color = Color.LightGray, shape = CircleShape)
            .padding(3.dp)
            .clip(CircleShape),
        contentScale = scale
    )
}