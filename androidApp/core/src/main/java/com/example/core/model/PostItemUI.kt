package com.example.core.model

data class PostItemUI(
    val id: Long,
    val user: UserUI,
    val title: String,
    val description: String? = null,
    val mediaUI: MediaUI,
    val hasAnimatedMedia: Boolean = false,
)

fun List<Post>.mapToPostItemUIs(): List<PostItemUI> {
    return this.map { post ->
        PostItemUI(
            id = post.id,
            user = UserUI(
                id = post.user.id,
                username = post.user.userName,
                profileImage = post.user.profileImage
            ),
            title = post.caption,
            mediaUI = MediaUI(
                id = post.media[0].id,
                image = post.media[0].mediaResolutions[0].url,
                video = if (post.media.size == 2) post.media[1].mediaResolutions[0].url else null,
                width = post.media[0].mediaResolutions[0].width,
                height = post.media[0].mediaResolutions[0].height,
            )
        )
    }
}