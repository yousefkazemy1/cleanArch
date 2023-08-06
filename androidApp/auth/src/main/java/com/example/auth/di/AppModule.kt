package com.example.auth.di

import com.example.authdomain.repository.AuthRepository
import com.example.authdomain.usecase.LoginUseCase
import com.example.authdomain.usecase.SignUpUseCase
import com.example.core.utils.logger.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Named

@InstallIn(ViewModelComponent::class)
@Module()
object AppModule {

    @Provides
    fun provideLoginUseCase(
        authRepository: AuthRepository,
        @Named("console_logger") logger: Logger,
    ): LoginUseCase = LoginUseCase(
        repository = authRepository,
        logger = logger
    )

    @Provides
    fun provideSignUpUseCase(
        authRepository: AuthRepository,
        @Named("console_logger") logger: Logger,
    ): SignUpUseCase = SignUpUseCase(
        repository = authRepository,
        logger = logger
    )
}