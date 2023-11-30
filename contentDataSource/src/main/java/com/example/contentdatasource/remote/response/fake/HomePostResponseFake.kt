package com.example.contentdatasource.remote.response.fake

import com.example.core.model.Media
import com.example.core.model.MediaResolution
import com.example.core.model.MediaType
import com.example.core.model.Post
import com.example.core.model.User
import com.example.core.response.mapToMedias
import com.example.core.response.mapToUser
import com.google.gson.annotations.SerializedName

data class HomePostResponseFake(
    @SerializedName("page") val page: Int,

    @SerializedName("per_page") val perPage: Int,

    @SerializedName("videos") val videos: List<Video>,
) {
    data class Video(
        @SerializedName("id") val id: Long,

        @SerializedName("duration") val duration: Int,

        @SerializedName("user") val user: User,

        @SerializedName("video_files") val videoFiles: List<VideoFile>,

        @SerializedName("video_pictures") val videoPics: List<VideoPic>,
    ) {
        data class User(
            @SerializedName("id") val id: Long,

            @SerializedName("name") val name: String,
        )

        data class VideoFile(
            @SerializedName("id") val id: Long,

            @SerializedName("quality") val quality: String,

            @SerializedName("width") val width: Int,

            @SerializedName("height") val height: Int,

            @SerializedName("link") val link: String,
        )

        data class VideoPic(
            @SerializedName("id") val id: Long,

            @SerializedName("picture") val picture: String,
        )
    }
}

fun HomePostResponseFake.mapToPosts(): List<Post> {
    return this.videos.map { post ->
        Post(
            id = post.id,
            caption = "",
            media = convertMedia(post.videoFiles, post.videoPics),
            user = User(
                id = post.user.id,
                userName = post.user.name,
                name = post.user.name,
            )
        )
    }
}

private fun convertMedia(
    videoFiles: List<HomePostResponseFake.Video.VideoFile>,
    videoPics: List<HomePostResponseFake.Video.VideoPic>
): List<Media> {
    val sdVideo = videoFiles.filter { it.quality == "sd" }
    val hdVideo = videoFiles.filter { it.quality == "hd" }

    val video = if (sdVideo.isNotEmpty()) sdVideo.first() else hdVideo.first()
    val picture = videoPics.last()

    return listOf(
        Media(
            0,
            type = MediaType.IMAGE,
            mediaResolutions = listOf(
                MediaResolution(
                    url = picture.picture,
                    width = video.width.toUShort(),
                    height = video.height.toUShort(),
                )
            )
        ),
        Media(
            0,
            type = MediaType.IMAGE,
            mediaResolutions = listOf(
                MediaResolution(
                    url = video.link,
                    width = video.width.toUShort(),
                    height = video.height.toUShort(),
                )
            )
        )
    )
}