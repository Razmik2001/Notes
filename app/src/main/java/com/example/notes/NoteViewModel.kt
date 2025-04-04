package com.example.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {
    private val _selectedType = MutableStateFlow<String?>(null)
    val selectedType: StateFlow<String?> = _selectedType
    var title = MutableStateFlow("")
    var content = MutableStateFlow("")
    var type = MutableStateFlow("")
    var timestamp = MutableStateFlow(0L)
    var date = MutableStateFlow(0L)
    var isDone = MutableStateFlow(false)

    var _types = MutableStateFlow<List<String>>(emptyList())



    val notes: StateFlow<List<Note>> = selectedType
        .flatMapLatest { type ->
            if (type == null) {
                repository.getAllNotes()
            } else {
                repository.getNotesByType(type)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    fun setType(type: String?) {
        _selectedType.value = type
    }

    fun getAllTypes() {
        viewModelScope.launch {
            val allNotes = repository.getAllNotesOnce() // ⚠️ см. ниже как реализовать
            val uniqueTypes = allNotes.map { it.type }.distinct()
            _types.value = uniqueTypes
        }
    }

    fun addNote(title: String, content: String, type: String, date: Long,isDone: Boolean) {
        viewModelScope.launch {
            val note = Note(
                title = title,
                content = content,
                type = type,
                date = date,
                isDone = isDone
            )
            repository.insert(note)
        }
    }
    fun updateNote(note: Note) {
        viewModelScope.launch {
            repository.update(note)
        }
    }
    fun deleteNote(note: Note) {
        viewModelScope.launch {
            repository.delete(note)
        }
    }
    fun toggleNoteDone(note: Note) {
        viewModelScope.launch {
            val updated = note.copy(isDone = !note.isDone)
            repository.update(updated)
        }
    }
}












