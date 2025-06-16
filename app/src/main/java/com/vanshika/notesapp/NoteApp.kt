package com.vanshika.notesapp

import android.app.Application
import androidx.room.Room
import com.vanshika.notesapp.feature_note.data.data_source.NoteDatabase
import com.vanshika.notesapp.feature_note.data.repository.NoteRepositoryImplementation
import com.vanshika.notesapp.feature_note.domain.repository.noteRepository
import com.vanshika.notesapp.feature_note.domain.use_case.DeleteNote
import com.vanshika.notesapp.feature_note.domain.use_case.GetNote
import com.vanshika.notesapp.feature_note.domain.use_case.GetNotes
import com.vanshika.notesapp.feature_note.domain.use_case.NoteUseCases
import com.vanshika.notesapp.feature_note.domain.use_case.addNote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@HiltAndroidApp
class NoteApp : Application()