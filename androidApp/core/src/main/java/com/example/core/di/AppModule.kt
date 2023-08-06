package com.example.core.di

import com.example.authdomain.repository.CredentialRepository
import com.example.authdomain.usecase.GetCredentialUseCase
import com.example.authdomain.usecase.SaveCredentialUseCase
import com.example.core.utils.logger.ConsoleLogger
import com.example.core.utils.logger.Logger
import com.example.core.utils.login.LoginStatus
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module()
object AppModule {

    @Singleton
    @Provides
    @Named("console_logger")
    fun provideConsoleLogger(): Logger = ConsoleLogger()

    @Singleton
    @Provides
    fun provideSaveCredentialUseCase(
        credentialRepository: CredentialRepository
    ): SaveCredentialUseCase = SaveCredentialUseCase(
        repository = credentialRepository
    )

    @Singleton
    @Provides
    fun provideGetCredentialUseCase(
        credentialRepository: CredentialRepository
    ): GetCredentialUseCase = GetCredentialUseCase(
        repository = credentialRepository
    )

    @Singleton
    @Provides
    fun provideLoginStatus(): LoginStatus = LoginStatus
}