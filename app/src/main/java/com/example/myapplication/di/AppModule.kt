package com.example.myapplication.di

import android.content.Context
import androidx.room.Room
import com.example.myapplication.data.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        "app_database"
    ).allowMainThreadQueries()
        .createFromAsset("database/theme.db")
        .build()

    @Singleton
    @Provides
    fun provideThemeDao(db: AppDatabase) =
        db.getThemeDao()

    @Singleton
    @Provides
    fun providePhraseDao(db: AppDatabase) =
        db.getPhraseDao()

    @Singleton
    @Provides
    fun providePersonalDao(db: AppDatabase) =
        db.getPersonalDao()
}