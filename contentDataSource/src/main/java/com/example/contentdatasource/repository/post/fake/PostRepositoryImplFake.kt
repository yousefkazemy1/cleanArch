package com.example.contentdatasource.repository.post.fake

import com.example.contentdomain.repository.PostRepository
import com.example.core.model.*

class PostRepositoryImplFake : PostRepository {
    override suspend fun getPosts(): Result<List<Post>> {
        return Result.Success(
            listOf(
                Post(
                    id = 1, caption = "The Shawshank Redemption", media = listOf(
                        Media(
                            id = 1, type = MediaType.IMAGE, mediaResolutions = listOf(
                                MediaResolution(
                                    url = "https://moviesapi.ir/images/tt0111161_screenshot1.jpg",
                                    width = 291u,
                                    height = 163u
                                )
                            )
                        )
                    ), user = User(
                        id = 1,
                        userName = "The Shawshank Redemption",
                        name = "Yousef Kazemu",
                        profileImage = "https://moviesapi.ir/images/tt0111161_poster.jpg"
                    )
                ), Post(
                    id = 2, caption = "The Godfather", media = listOf(
                        Media(
                            id = 1, type = MediaType.IMAGE, mediaResolutions = listOf(
                                MediaResolution(
                                    url = "https://moviesapi.ir/images/tt0068646_screenshot1.jpg",
                                    width = 291u,
                                    height = 163u
                                )
                            )
                        )
                    ), user = User(
                        id = 2,
                        userName = "The Godfather",
                        name = "Bahareh",
                        profileImage = "https://moviesapi.ir/images/tt0068646_poster.jpg"
                    )
                ),
                Post(
                    id = 3, caption = "The Godfather: Part II", media = listOf(
                        Media(
                            id = 1, type = MediaType.IMAGE, mediaResolutions = listOf(
                                MediaResolution(
                                    url = "https://moviesapi.ir/images/tt0071562_screenshot1.jpg",
                                    width = 291u,
                                    height = 163u
                                )
                            )
                        )
                    ), user = User(
                        id = 3,
                        userName = "The Godfather: Part II",
                        name = "Ali",
                        profileImage = "https://moviesapi.ir/images/tt0071562_poster.jpg"
                    )
                ),
                Post(
                    id = 4, caption = "The Dark Knight", media = listOf(
                        Media(
                            id = 1, type = MediaType.IMAGE, mediaResolutions = listOf(
                                MediaResolution(
                                    url = "https://moviesapi.ir/images/tt0468569_screenshot1.jpg",
                                    width = 291u,
                                    height = 121u
                                )
                            )
                        )
                    ), user = User(
                        id = 4,
                        userName = "The Dark Knight",
                        name = "Nazanin",
                        profileImage = "https://moviesapi.ir/images/tt0468569_poster.jpg"
                    )
                ),
                Post(
                    id = 5, caption = "Superman", media = listOf(
                        Media(
                            id = 1, type = MediaType.IMAGE, mediaResolutions = listOf(
                                MediaResolution(
                                    url = "http://s14.ir-android.com/joke_city/PrivatePictures100/17399729/1690505077487_606x1366.jpg",
                                    width = 606u,
                                    height = 1366u
                                )
                            )
                        )
                    ), user = User(
                        id = 5,
                        userName = "Superman",
                        name = "Nazanin",
                        profileImage = "https://moviesapi.ir/images/tt0468569_poster.jpg"
                    )
                )
            )
        )
    }
}