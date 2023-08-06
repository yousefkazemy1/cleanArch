package com.example.contentdatasource.remote.response

import com.example.core.model.*
import com.example.core.response.mapToMedias
import com.example.core.response.mapToUser
import com.google.gson.annotations.SerializedName

data class HomePostResponse(
    @SerializedName("total_posts")
    val totalPosts: Long,

    @SerializedName("page")
    val page: Long,

    @SerializedName("posts")
    val posts: List<PostResponse>,
)

fun HomePostResponse.mapToPosts(): List<Post> {
    return this.posts.map { post ->
        Post(
            id = post.id,
            caption = post.caption,
            media = post.media.mapToMedias(),
            user = post.user.mapToUser()
        )
    }
}

