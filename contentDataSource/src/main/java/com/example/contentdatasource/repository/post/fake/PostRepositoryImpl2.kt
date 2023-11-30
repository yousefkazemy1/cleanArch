package com.example.contentdatasource.repository.post.fake

import com.example.contentdatasource.remote.PostApi
import com.example.contentdatasource.remote.response.fake.mapToPosts
import com.example.contentdatasource.remote.response.mapToPosts
import com.example.contentdomain.repository.PostRepository
import com.example.core.model.ErrorCause
import com.example.core.model.ErrorResult
import com.example.core.model.Post
import com.example.core.model.Result
import com.example.core.utils.convertToString
import com.example.data.utils.convertToErrorResponse

class PostRepositoryImpl2(
    private val postApi: PostApi,
) : PostRepository {
    override suspend fun getPosts(): Result<List<Post>> {
        return try {
            postApi.getHomePosts(perPage = 10).let { response ->
                if (response.isSuccessful && response.body() != null) {
                    Result.Success(response.body()!!.mapToPosts())
                } else if (response.errorBody() != null && response.code() != 500) {
                    val result = response.errorBody()!!.byteStream().convertToString()
                        .convertToErrorResponse()
                    Result.Error(
                        ErrorResult(
                            errorCause = ErrorCause.API, errorResponse = result.message
                        )
                    )
                } else {
                    Result.Error(ErrorResult(errorCode = 500, errorCause = ErrorCause.API))
                }
            }
        } catch (e: Exception) {
            Result.Error(
                ErrorResult(
                    errorCode = 500,
                    errorCause = ErrorCause.EXCEPTION,
                    errorResponse = e.message ?: ""
                )
            )
        }
    }
}