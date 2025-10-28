package com.vanshika.notesapp.feature_note.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthProvider

interface AuthRepository {
    suspend fun signInWithGoogle(idToken: String): Result<FirebaseUser>
    suspend fun sendOTP(phoneNumber: String, callback: PhoneAuthProvider.OnVerificationStateChangedCallbacks)
    suspend fun verifyOtp(verificationId: String, otp: String): Result<FirebaseUser>
    fun logout()
}