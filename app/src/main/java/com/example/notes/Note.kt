package com.example.notes


import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @NonNull
    val title: String,
    val content: String,
    @NonNull
    val type: String, // например: "спорт", "работа", "личное"
    val timestamp: Long = System.currentTimeMillis(),
    val date: Long,
    val isDone: Boolean = false,
)