package com.example.myapplication.data.room.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.data.model.Personal
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonalDao {
    @Insert
    suspend fun insertAccount(account: Personal)

    @Update
    suspend fun updateAccount(account: Personal)

    @Delete
    suspend fun deleteAccount(account: Personal)

    @Query("SELECT * FROM personal ORDER BY id DESC")
    fun getAccount(): Flow<Personal>
}