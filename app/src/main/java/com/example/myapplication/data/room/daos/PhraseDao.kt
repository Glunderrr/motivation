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

    // Повертає всі фрази, відсортовані від найновішої до найстарішої, як реактивний потік
    @Query("SELECT * FROM phrases ORDER BY id DESC")
    fun getAll(): Flow<List<Phrase>>

    // Повертає лише улюблені фрази (isLiked == true) як реактивний потік
    @Query("SELECT * FROM phrases WHERE isLiked == 1 ORDER BY id DESC")
    fun getAllLiked(): Flow<List<Phrase>>

    // Повертає лише власні фрази користувача (isOwn == true) як реактивний потік
    @Query("SELECT * FROM phrases WHERE isOwn == 1 ORDER BY id DESC")
    fun getAllOwn(): Flow<List<Phrase>>

    // Оновлює один або кілька існуючих записів фраз у базі даних
    @Update
    suspend fun update(vararg phrase: Phrase)

    // Вставляє нову або оновлює існуючу фразу (upsert = insert or update)
    @Upsert
    suspend fun upsert(phrase: Phrase)

    // Видаляє список фраз з бази даних
    @Delete
    suspend fun deleteAll(phrase: List<Phrase>)
}
