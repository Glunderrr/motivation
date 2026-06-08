package com.example.myapplication.data.room.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.data.model.Theme
import kotlinx.coroutines.flow.Flow

@Dao
interface ThemeDao {

    // Повертає всі теми, відсортовані від найновішої до найстарішої, як реактивний потік
    @Query("SELECT * FROM themes ORDER BY id DESC")
    fun getAll(): Flow<List<Theme>>

    // Додає набір тем до бази даних
    @Insert
    suspend fun insertAll(vararg themes: Theme)

    // Видаляє набір тем з бази даних
    @Delete
    suspend fun deleteAll(vararg theme: Theme)
}
