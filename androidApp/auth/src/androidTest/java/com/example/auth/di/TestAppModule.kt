package com.example.auth.di

import com.example.authdatasource.repository.fake.AuthRepositoryImplFake
import com.example.authdomain.model.Auth
import com.example.authdomain.repository.AuthRepository
import com.example.authdomain.usecase.LoginUseCase
import com.example.authdomain.usecase.SignUpUseCase
import com.example.core.utils.logger.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.runBlocking
import javax.inject.Named

@InstallIn(ViewModelComponent::class)
@Module()
object TestAppModule {

    @Provides
    fun provideAuthRepository(): AuthRepository {
        val repository = AuthRepositoryImplFake()
        runBlocking {
            repository.signUp(auth = Auth(email = "sample@gmail.com", password = "Az1234!"))
        }
        return repository
    }

    @Provides
    fun provideLogger() : Logger{
        return object : Logger {
            override fun log(vararg message: String) {
                println()
            }
        }
    }

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