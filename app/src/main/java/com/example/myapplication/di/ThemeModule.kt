package com.example.myapplication.di

import com.example.myapplication.data.room.daos.ThemeDao
import com.example.myapplication.repository.classes.ThemesRepositoryImpl
import com.example.myapplication.repository.interfaces.ThemesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ThemeModule {
    @Singleton
    @Provides
    fun provideThemeRepository(dao: ThemeDao): ThemesRepository {
        return ThemesRepositoryImpl(dao)
    }
}