package com.example.myapplication.data.room.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.myapplication.data.model.Personal
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonalDao {
    @Upsert
    suspend fun upsertPersonal(account: Personal)
    @Delete
    suspend fun deletePersonal(account: Personal)

    @Query("SELECT * FROM personal ORDER BY id DESC LIMIT 1")
    fun getPersonal(): Flow<Personal?>
}