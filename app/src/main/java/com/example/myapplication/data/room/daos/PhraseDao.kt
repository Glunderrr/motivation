package com.example.myapplication.data.room.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.myapplication.data.model.Phrase
import kotlinx.coroutines.flow.Flow

@Dao
interface PhraseDao {

    @Query("SELECT * FROM phrases ORDER BY id DESC")
    fun getAll(): Flow<List<Phrase>>

    @Query("SELECT * FROM phrases WHERE isLiked == 1 ORDER BY id DESC")
    fun getAllLiked(): Flow<List<Phrase>>

    @Query("SELECT * FROM phrases WHERE isOwn == 1 ORDER BY id DESC")
    fun getAllOwn(): Flow<List<Phrase>>

    @Update
    suspend fun update(vararg phrase: Phrase)

    @Upsert
    suspend fun upsert(phrase: Phrase)

    @Delete
    suspend fun deleteAll(phrase: List<Phrase>)
}