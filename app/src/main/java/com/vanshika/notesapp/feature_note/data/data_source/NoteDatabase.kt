package com.vanshika.notesapp.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vanshika.notesapp.feature_note.domain.model.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase: RoomDatabase() {

    abstract  val NoteDao: noteDao

    companion object{
        const val DATABASE_NAME = "notes_db"
    }
}