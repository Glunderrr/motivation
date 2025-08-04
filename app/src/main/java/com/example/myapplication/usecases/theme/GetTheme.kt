package com.example.myapplication.usecases.theme

import com.example.myapplication.data.model.Theme
import com.example.myapplication.repository.interfaces.ThemesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTheme @Inject constructor(
    private val repository: ThemesRepository
) {
    suspend fun invoke(): Flow<List<Theme>> = repository.getThemes()
}