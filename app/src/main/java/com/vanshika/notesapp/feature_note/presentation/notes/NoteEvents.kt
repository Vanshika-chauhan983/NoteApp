package com.vanshika.notesapp.feature_note.presentation.notes

import com.vanshika.notesapp.feature_note.domain.model.Note

sealed class NoteEvents {
    data class NoteOrder(val noteOrder:com.vanshika.notesapp.feature_note.domain.util.NoteOrder): NoteEvents()
    data class DeleteNote(val note: Note):NoteEvents()
    data object RestoreNote:NoteEvents()
    data object ToggleOrderSection:NoteEvents()
}