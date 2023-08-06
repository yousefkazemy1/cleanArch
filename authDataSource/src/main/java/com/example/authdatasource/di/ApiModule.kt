package com.example.authdatasource.di

import android.content.Context
import android.content.SharedPreferences
import com.example.authdatasource.remote.AuthApi
import com.example.authdatasource.repository.CredentialRepositoryImpl
import com.example.authdatasource.repository.fake.AuthRepositoryImplFake
import com.example.authdomain.repository.AuthRepository
import com.example.authdomain.repository.CredentialRepository
import com.example.core.utils.logger.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(ViewModelComponent::class)
@Module()
object ApiModule {

    @Provides
    fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @Provides
    fun provideAuthRepository(
        authApi: AuthApi,
        @Named("console_logger") logger: Logger
    ): AuthRepository {
//        return AuthRepositoryImpl(authApi = authApi, logger = logger)
        return AuthRepositoryImplFake()
    }
}

@InstallIn(SingletonComponent::class)
@Module()
object AppModule {
    @Singleton
    @Provides
    @Named("sharedPref_credential")
    fun provideCredentialSharedPref(
        @ApplicationContext context: Context
    ): SharedPreferences = context.getSharedPreferences("shared.credential", Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideCredentialRepository(
        @Named("sharedPref_credential") sharedPreferences: SharedPreferences
    ): CredentialRepository = CredentialRepositoryImpl(sharedPreferences = sharedPreferences)
}