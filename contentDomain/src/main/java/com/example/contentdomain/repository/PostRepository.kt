package com.example.contentdomain.repository
import com.example.core.model.Post
import com.example.core.model.Result

interface PostRepository {
    suspend fun getPosts(): Result<List<Post>>
}