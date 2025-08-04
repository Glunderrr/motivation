package com.example.myapplication.repository.interfaces

import com.example.myapplication.data.model.Personal
import kotlinx.coroutines.flow.Flow

interface PersonalRepository {
    suspend fun insertAccount(account: Personal)
    suspend fun updateAccount(account: Personal)
    suspend fun deleteAccount(account: Personal)
    fun getAccount(): Flow<Personal>
}