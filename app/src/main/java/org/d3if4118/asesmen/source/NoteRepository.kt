package org.d3if4118.asesmen.source

import androidx.lifecycle.LiveData
import org.d3if4118.asesmen.source.local.NoteDao
import org.d3if4118.asesmen.model.Note

class NoteRepository(private val noteDao: NoteDao) {

    val readAllData: LiveData<List<Note>> = noteDao.readAllData()

    suspend fun addNote(note: Note) {
        noteDao.addNote(note)
    }

    suspend fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }

    suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }

    suspend fun deleteAllMote() {
        noteDao.deleteAll()
    }

}