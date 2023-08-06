package com.example.contentdatasource.di

import com.example.contentdatasource.remote.PostApi
import com.example.contentdatasource.repository.post.fake.PostRepositoryImplFake
import com.example.contentdomain.repository.PostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@InstallIn(ViewModelComponent::class)
@Module()
object ApiModule {
    @Provides
    fun providePostRepository(
        postApi: PostApi
    ): PostRepository {
//        return PostRepositoryImpl(postApi)
        return PostRepositoryImplFake()
    }
}