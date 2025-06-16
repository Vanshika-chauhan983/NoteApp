package com.vanshika.notesapp.feature_note.domain.use_case

import com.vanshika.notesapp.feature_note.domain.model.Note
import com.vanshika.notesapp.feature_note.domain.repository.noteRepository

class DeleteNote(
    private val repository: noteRepository
) {
    suspend operator fun invoke(note: Note){
        repository.deleteNote(note)
    }
}