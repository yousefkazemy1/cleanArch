package com.example.core.component.ui.compose

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.model.PostItemUI
import com.example.core.model.UserUI

@Composable
fun ProfileItemView(
    modifier: Modifier = Modifier,
    user: UserUI
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 12.dp, top = 4.dp, end = 12.dp, bottom = 4.dp)
    ) {
        RoundedAsyncImageView(
            modifier = Modifier
                .padding(end = 6.dp)
                .size(38.dp)
                .testTag("user_image"),
            imageUrl = user.profileImage,
            scale = ContentScale.Crop
        )

        Text(
            modifier = Modifier.testTag("username_text"),
            text = user.username,
            fontSize = 18.sp,
            color = Color.Black
        )
    }
}