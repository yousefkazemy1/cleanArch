package com.example.contentdomain.usecase

import com.example.contentdomain.repository.PostRepository
import com.example.core.model.Post
import com.example.core.model.Result
import com.example.core.utils.login.LoginStatus

class GetHomePostsUseCase(
    private val repository: PostRepository,
    private val loginStatus: LoginStatus
) {
    suspend operator fun invoke(): Result<List<Post>> {
        if (!loginStatus.isUserLoggedIn) {
            throw RuntimeException("User hasn't logged in to get home posts")
        }

        return repository.getPosts();
    }
}