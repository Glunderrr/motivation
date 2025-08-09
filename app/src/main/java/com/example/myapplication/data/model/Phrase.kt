package com.example.myapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Date

@Serializable
@Entity(tableName = "phrases")
data class Phrase(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val phrase: String = "",
    val theme: String = "",
    val isLiked: Boolean = false,
    val isOwn: Boolean = true,
    val date: String = SimpleDateFormat("dd-MM-yyyy").format(Date())
)