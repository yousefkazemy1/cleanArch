package com.example.core.model

data class Media(
    val id: Long = 0,
    val type: MediaType,
    val mediaResolutions: List<MediaResolution>
)

data class MediaResolution(
    val url: String,
    val width: UShort = 0u,
    val height: UShort = 0u,
)

enum class MediaType {
    IMAGE, VIDEO
}