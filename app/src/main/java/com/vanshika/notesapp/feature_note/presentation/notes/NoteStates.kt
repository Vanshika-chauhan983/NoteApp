package com.vanshika.notesapp.feature_note.presentation.notes

import com.vanshika.notesapp.feature_note.domain.model.Note
import com.vanshika.notesapp.feature_note.domain.util.NoteOrder
import com.vanshika.notesapp.feature_note.domain.util.OrderType

data class NoteStates(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
