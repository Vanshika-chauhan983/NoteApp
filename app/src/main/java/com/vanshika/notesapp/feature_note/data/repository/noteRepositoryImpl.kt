package com.vanshika.notesapp.feature_note.data.repository

import com.vanshika.notesapp.feature_note.data.data_source.noteDao
import com.vanshika.notesapp.feature_note.domain.model.Note
import com.vanshika.notesapp.feature_note.domain.repository.noteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImplementation(
    private val dao:noteDao
):noteRepository {

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }

}