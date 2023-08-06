package com.example.cleanarchproject.di

import com.example.contentdomain.repository.PostRepository
import com.example.contentdomain.usecase.GetHomePostsUseCase
import com.example.core.utils.login.LoginStatus
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module()
class AppModule {
    @Provides
    fun provideGetHomePostsUseCase(
        postRepository: PostRepository,
        loginStatus: LoginStatus
    ): GetHomePostsUseCase = GetHomePostsUseCase(
        repository = postRepository,
        loginStatus = loginStatus
    )
}