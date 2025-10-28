package com.vanshika.notesapp.feature_note.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.vanshika.notesapp.feature_note.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit

class AuthRepositoryImpl(
    private val auth: FirebaseAuth
) : AuthRepository {
    override suspend fun signInWithGoogle(idToken: String): Result<FirebaseUser> {
        return try {
            val googleCredential = GoogleAuthProvider.getCredential(idToken, null)
            val result = auth.signInWithCredential(googleCredential).await()
            result.user?.let {
                Result.success(it)
            } ?: Result.failure(Exception("User is null"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun verifyOtp(
        verificationId: String,
        otp: String
    ): Result<FirebaseUser> {
        return try {
            val credential = PhoneAuthProvider.getCredential(verificationId, otp)
            val result = auth.signInWithCredential(credential).await()
            result.user?.let {
                Result.success(it)
            } ?: Result.failure(Exception("User is null"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun logout() {
        FirebaseAuth.getInstance().signOut()
    }

    override suspend fun sendOTP(
        phoneNumber: String,
        callback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setCallbacks(callback)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}