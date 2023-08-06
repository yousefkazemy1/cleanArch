package com.example.core.response

import com.example.core.model.Media
import com.example.core.model.MediaType
import com.google.gson.annotations.SerializedName

data class MediaResponse(
    @SerializedName("id")
    val id: Long = 0,

    @SerializedName("type")
    val type: String,

    @SerializedName("resolutions")
    val mediaResolutions: List<MediaResolution>
)

data class MediaResolution(
    @SerializedName("url")
    val url: String,

    @SerializedName("width")
    val width: UShort = 0u,

    @SerializedName("height")
    val height: UShort = 0u,
)

fun List<MediaResponse>.mapToMedias(): List<Media> {
    return this.map { media ->
        Media(
            id = media.id,
            type = media.type.mapToMediaType(),
            mediaResolutions = media.mediaResolutions.map { resolution ->
                com.example.core.model.MediaResolution(
                    url = resolution.url,
                    width = resolution.width,
                    height = resolution.height
                )
            }
        )
    }
}

fun String.mapToMediaType(): MediaType {
    return when (this) {
        "image" -> {
            MediaType.IMAGE
        }
        "video" -> {
            MediaType.VIDEO
        }
        else -> {
            MediaType.IMAGE
        }
    }
}