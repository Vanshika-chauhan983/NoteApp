package com.vanshika.notesapp.feature_note.domain.use_case

import com.vanshika.notesapp.feature_note.domain.model.Note
import com.vanshika.notesapp.feature_note.domain.repository.noteRepository

class GetNote(
    private val repository: noteRepository
){

    suspend operator fun invoke(id: Int):Note?{
        return repository.getNoteById(id)
    }
}