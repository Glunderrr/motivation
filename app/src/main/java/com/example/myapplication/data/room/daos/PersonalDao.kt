package com.example.myapplication.data.room.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.myapplication.data.model.Personal
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonalDao {

    // Вставляє або оновлює запис особистих даних користувача
    @Upsert
    suspend fun upsertPersonal(account: Personal)

    // Видаляє запис особистих даних користувача
    @Delete
    suspend fun deletePersonal(account: Personal)

    // Повертає найновіший запис особистих даних як реактивний потік; null якщо профіль ще не створений
    @Query("SELECT * FROM personal ORDER BY id DESC LIMIT 1")
    fun getPersonal(): Flow<Personal?>
}
