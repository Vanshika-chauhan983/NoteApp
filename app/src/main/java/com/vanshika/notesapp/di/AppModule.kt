package com.vanshika.notesapp.di

import android.app.Application
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.vanshika.notesapp.feature_note.data.data_source.NoteDatabase
import com.vanshika.notesapp.feature_note.data.repository.AuthRepositoryImpl
import com.vanshika.notesapp.feature_note.data.repository.NoteRepositoryImplementation
import com.vanshika.notesapp.feature_note.domain.repository.AuthRepository
import com.vanshika.notesapp.feature_note.domain.repository.noteRepository
import com.vanshika.notesapp.feature_note.domain.use_case.DeleteNote
import com.vanshika.notesapp.feature_note.domain.use_case.GetNote
import com.vanshika.notesapp.feature_note.domain.use_case.GetNotes
import com.vanshika.notesapp.feature_note.domain.use_case.NoteUseCases
import com.vanshika.notesapp.feature_note.domain.use_case.addNote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): noteRepository {
        return NoteRepositoryImplementation(db.NoteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUsecases(noteRepository: noteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(noteRepository),
            deleteNote = DeleteNote(noteRepository),
            addNote = addNote(noteRepository),
            getNote = GetNote(noteRepository)
        )
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(auth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(auth)
    }
}