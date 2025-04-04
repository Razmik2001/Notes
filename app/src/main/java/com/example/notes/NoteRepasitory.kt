package com.example.notes

import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {
    fun getAllNotes(): Flow<List<Note>> = noteDao.getAllNotes()
    fun getNotesByType(type: String): Flow<List<Note>> = noteDao.getNotesByType(type)
    fun getNotesByDate(dateMillis: Long): Flow<List<Note>> = noteDao.getNotesByDate(dateMillis)
    fun getPendingNotes(): Flow<List<Note>> = noteDao.getPendingNotes()
    suspend fun getAllNotesOnce(): List<Note> {
        return noteDao.getAllNotesOnce()
    }

    suspend fun insert(note: Note) = noteDao.insert(note)
    suspend fun update(note: Note) = noteDao.update(note)
    suspend fun delete(note: Note) = noteDao.delete(note)
}