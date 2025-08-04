package com.example.myapplication.di

import com.example.myapplication.data.network.ApiService
import com.example.myapplication.data.room.daos.PhraseDao
import com.example.myapplication.repository.classes.ApiPhraseRepositoryImpl
import com.example.myapplication.repository.classes.PhraseRepositoryImpl
import com.example.myapplication.repository.interfaces.ApiPhraseRepository
import com.example.myapplication.repository.interfaces.PhraseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PhraseModule {
    @Singleton
    @Provides
    fun providePhraseRepository(dao: PhraseDao): PhraseRepository {
        return PhraseRepositoryImpl(dao)
    }

    @Singleton
    @Provides
    fun provideApiPhraseRepository(apiService: ApiService): ApiPhraseRepository {
        return ApiPhraseRepositoryImpl(apiService)
    }
}