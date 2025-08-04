package com.example.myapplication.repository.interfaces

import com.example.myapplication.data.model.Theme
import kotlinx.coroutines.flow.Flow

interface ThemesRepository {
    suspend fun getThemes(): Flow<List<Theme>>
    suspend fun addThemes(vararg themes: Theme)
    suspend fun deleteAll(vararg theme: Theme)
}