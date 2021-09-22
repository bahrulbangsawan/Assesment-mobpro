package org.d3if4118.asesmen.mvvm.crud

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import org.d3if4118.asesmen.source.local.NoteDatabase
import org.d3if4118.asesmen.source.NoteRepository
import org.d3if4118.asesmen.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<Note>>
    private val repository: NoteRepository

    init {
        val noteDao = NoteDatabase.getDatabase(
            application
        ).noteDao()
        repository = NoteRepository(noteDao)
        readAllData = repository.readAllData
    }

    fun addNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNote(note)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNote(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(note)
        }
    }

    fun deleteAllNote() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllMote()
        }
    }

}