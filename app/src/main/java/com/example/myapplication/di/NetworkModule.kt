package com.example.myapplication.di

import com.example.myapplication.BuildConfig
import com.example.myapplication.data.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideInterceptor() = Interceptor { chain ->
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${BuildConfig.MISTRAL_AI_API_KEY}")
            .build()
        chain.proceed(newRequest)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: Interceptor) = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.mistral.ai/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}