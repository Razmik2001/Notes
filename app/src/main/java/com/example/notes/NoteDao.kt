package com.example.notes

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY date ASC")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE type = :type ORDER BY date ASC")
    fun getNotesByType(type: String): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE isDone = 0 ORDER BY date ASC")
    fun getPendingNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE date = :dateMillis")
    fun getNotesByDate(dateMillis: Long): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM notes")
    suspend fun getAllNotesOnce(): List<Note>
}