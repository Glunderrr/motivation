package com.example.myapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "personal")
data class Personal(
    @PrimaryKey val id: Int = 0,
    val name: String = "",
    val status: String = "",
    val image: String? = null,
)
