package com.vanshika.notesapp.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vanshika.notesapp.ui.theme.BabyBlue
import com.vanshika.notesapp.ui.theme.LightGreen
import com.vanshika.notesapp.ui.theme.RedOrange
import com.vanshika.notesapp.ui.theme.RedPink
import com.vanshika.notesapp.ui.theme.Violet

@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
){
    companion object{
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

class InvalidNoteException(message: String): Exception(message)
