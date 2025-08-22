package com.example.myapplication.repository.interfaces

import com.example.myapplication.data.model.Personal
import kotlinx.coroutines.flow.Flow

interface PersonalRepository {
    suspend fun insertPersonal(account: Personal)
    suspend fun updatePersonal(account: Personal)
    suspend fun deletePersonal(account: Personal)
    fun getPersonal(): Flow<Personal?>
}