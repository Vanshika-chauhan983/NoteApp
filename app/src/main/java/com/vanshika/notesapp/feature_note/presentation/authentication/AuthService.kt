package com.vanshika.notesapp.feature_note.presentation.authentication

import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.vanshika.notesapp.R

class AuthService (
    context: Context,
    private val viewModel: Viewmodel
) {
    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.web_client_id))
        .requestEmail()
        .build()
    val googleSignInClient = GoogleSignIn.getClient(context, gso)

    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            credential.smsCode?.let { code ->
                viewModel.optChanged(code)
                val state = viewModel.authState.value
                viewModel.signInWithPhone(state.verificationId, state.otp)
            }
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Log.e("AuthScreen", "Verification failed", e)
        }

        override fun onCodeSent(
            id: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            viewModel.updateVerificationId(id)
        }
    }



    fun googleSignInResult(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.result
            val idToken = account.idToken
            if (idToken != null) {
                viewModel.signInWithGoogle(idToken)
            }
        } catch (e: Exception) {
            Log.e("AuthScreen", "Google sign-in failed", e)
        }
    }
}