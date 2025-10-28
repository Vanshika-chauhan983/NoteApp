package com.vanshika.notesapp.feature_note.presentation.util

sealed class Screen(val route: String) {
    object NoteScreen : Screen("note_screen")
    object AddEditNoteScreen : Screen("add_edit_note_screen")
    object AuthScreen : Screen("auth_screen")
    object OTPScreen : Screen("otp_screen")
    object ProfileScreen : Screen("profile_screen")
}