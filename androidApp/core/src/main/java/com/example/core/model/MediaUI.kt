package com.example.core.model

data class MediaUI(
    val id: Long = 0L,
    val image: String? = null,
    val video: String? = null,
    val width: UShort = 0u,
    val height: UShort = 0u
)