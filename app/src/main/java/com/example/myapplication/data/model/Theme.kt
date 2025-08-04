package com.example.myapplication.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "themes")
data class Theme(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "themeName") val themeName: String
)