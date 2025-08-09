package com.example.myapplication.repository.interfaces

import com.example.myapplication.data.model.Phrase
import kotlinx.coroutines.flow.Flow

interface PhraseRepository {
    fun getAll(): Flow<List<Phrase>>

    fun getAllLiked(): Flow<List<Phrase>>

    fun getAllOwn(): Flow<List<Phrase>>

    suspend fun upsert(phrase: Phrase)
    suspend fun update(vararg phrase: Phrase)

    suspend fun deleteAll(phrase: List<Phrase>)
}