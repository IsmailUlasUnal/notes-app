package com.example.notes_tutorial_app.database

import androidx.lifecycle.LiveData
import com.example.notes_tutorial_app.models.Note

class NoteRepository(private val noteDao: NoteDao) {

    val allNodes: LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note : Note) {
        noteDao.insert(note)
    }

    suspend fun delete(note: Note) {
        noteDao.delete(note)
    }

    suspend fun update(note: Note) {
        noteDao.update(note.id, note.title, note.note, note.color)
    }

}