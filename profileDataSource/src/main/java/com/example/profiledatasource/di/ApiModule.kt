package com.example.profiledatasource.di

import com.example.profiledatasource.remote.ProfileApi
import com.example.profiledatasource.repository.fake.ProfileRepositoryImplFake
import com.example.profiledomain.repository.ProfileRespository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit


@InstallIn(ViewModelComponent::class)
@Module()
object ApiModule {
    @Provides
    fun provideRepository(
        profileApi: ProfileApi,
    ): ProfileRespository {
//        return ProfileRepositoryImpl(profileApi)
        return ProfileRepositoryImplFake()
    }

    @Provides
    fun providePostApi(retrofit: Retrofit): ProfileApi = retrofit.create(ProfileApi::class.java)
}