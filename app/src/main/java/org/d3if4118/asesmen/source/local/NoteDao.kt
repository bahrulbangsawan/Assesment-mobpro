package org.d3if4118.asesmen.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import org.d3if4118.asesmen.model.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("DELETE FROM note_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM note_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Note>>
}