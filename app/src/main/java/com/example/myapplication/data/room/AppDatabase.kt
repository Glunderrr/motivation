package com.example.myapplication.data.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.data.model.Personal
import com.example.myapplication.data.model.Phrase
import com.example.myapplication.data.model.Theme
import com.example.myapplication.data.room.daos.PersonalDao
import com.example.myapplication.data.room.daos.PhraseDao
import com.example.myapplication.data.room.daos.ThemeDao


@Database(
    version = 4,
    entities = [Theme::class, Phrase::class, Personal::class],
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getThemeDao(): ThemeDao
    abstract fun getPhraseDao(): PhraseDao
    abstract fun getPersonalDao(): PersonalDao
}