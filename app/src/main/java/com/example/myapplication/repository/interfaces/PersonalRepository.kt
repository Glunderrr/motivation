package com.example.myapplication.repository.interfaces

import com.example.myapplication.data.model.Personal
import kotlinx.coroutines.flow.Flow

interface PersonalRepository {
    suspend fun upsertPersonal(personal: Personal)
    suspend fun deletePersonal(personal: Personal)
    fun getPersonal(): Flow<Personal?>
}