package com.example.core.model

data class Post(
    val id: Long,
    val caption: String,
    val media: List<Media>,
    val user: User
)