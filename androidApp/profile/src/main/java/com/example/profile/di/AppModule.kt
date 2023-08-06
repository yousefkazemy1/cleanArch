package com.example.profile.di

import com.example.core.utils.login.LoginStatus
import com.example.profiledomain.repository.ProfileRespository
import com.example.profiledomain.usecase.GetProfileUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module()
object AppModule {

    @Provides
    fun provideGetProfileUseCase(
        repository: ProfileRespository,
        loginStatus: LoginStatus
    ): GetProfileUseCase = GetProfileUseCase(
        repository = repository,
        loginStatus = loginStatus
    )
}