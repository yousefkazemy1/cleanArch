package com.example.core.component.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun RoundedImageViewPreview() {
    
}

@Composable
fun RoundedImageView(
    modifier: Modifier = Modifier,
    drawable: Int,
    contentDescription: String = "profile",
    aspectRatio: Float = 1f,
    borderWidth: Dp = 1.dp,
    borderColor: Color = Color(0xFF673AB7),
    borderPadding: Dp = 3.dp
) {
    Image(
        painter = painterResource(id = drawable),
        contentDescription = contentDescription,
        modifier = modifier
            .aspectRatio(aspectRatio, matchHeightConstraintsFirst = true)
            .border(
                width = borderWidth, color = borderColor, shape = CircleShape
            )
            .padding(borderPadding)
            .clip(CircleShape)
    )
}