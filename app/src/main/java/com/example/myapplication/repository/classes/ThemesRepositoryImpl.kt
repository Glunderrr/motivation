package com.example.myapplication.repository.classes

import com.example.myapplication.data.model.Theme
import com.example.myapplication.data.room.daos.ThemeDao
import com.example.myapplication.repository.interfaces.ThemesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ThemesRepositoryImpl @Inject constructor(
    private val dao: ThemeDao
) : ThemesRepository {
    override suspend fun getThemes(): Flow<List<Theme>> = dao.getAll()

    override suspend fun addThemes(vararg themes: Theme) = dao.insertAll(*themes)

    override suspend fun deleteAll(vararg theme: Theme) = dao.deleteAll(*theme)
}