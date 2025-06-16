package com.vanshika.notesapp.feature_note.domain.use_case

import com.vanshika.notesapp.feature_note.domain.model.InvalidNoteException
import com.vanshika.notesapp.feature_note.domain.model.Note
import com.vanshika.notesapp.feature_note.domain.repository.noteRepository

class addNote(
    private val repository: noteRepository
){
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note){
        if (note.title.isBlank()){
            throw InvalidNoteException("The title of the note can't be empty!")
        }
        if (note.content.isBlank()){
            throw InvalidNoteException("The content of the note can't be empty!")
        }
        repository.insertNote(note)
    }
}