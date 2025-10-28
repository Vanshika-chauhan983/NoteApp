package com.vanshika.notesapp.feature_note.presentation.authentication

import com.google.firebase.auth.FirebaseUser

data class AuthState(
    val phoneNumber: String = "",
    val otp: String = "",
    val verificationId: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val user: FirebaseUser? = null,
    var isCodeSent: Boolean = false
)