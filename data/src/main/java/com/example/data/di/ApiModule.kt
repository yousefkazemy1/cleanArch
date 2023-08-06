package com.example.data.di

import com.example.data.Endpoints
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module()
class ApiModule {

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory,
    ): Retrofit {
        return Retrofit.Builder().baseUrl(Endpoints.BASE_URL).addConverterFactory(converterFactory)
            .client(okHttpClient).build()
    }

    @Singleton
    @Provides
    fun providerOkhttpClient(): OkHttpClient =
        OkHttpClient.Builder().readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS).build()

    @Singleton
    @Provides
    fun provideGsonConvertorFactory(gson: Gson) = GsonConverterFactory.create(gson)

    @Singleton
    @Provides
    fun provideGson() = GsonBuilder().setLenient().create()
}