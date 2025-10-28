package com.vanshika.notesapp.feature_note.presentation.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.PhoneAuthProvider
import com.vanshika.notesapp.feature_note.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Viewmodel @Inject constructor(
    val repository: AuthRepository
): ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun onPhoneChanged(phone: String) {
        _authState.value = _authState.value.copy(phoneNumber = phone)
    }

    fun optChanged(otp: String) {
        _authState.value = _authState.value.copy(otp = otp)
    }

    fun updateVerificationId(verificationId: String) {
        _authState.value = _authState.value.copy(
            verificationId = verificationId,
            isCodeSent = true,
            isLoading = false)
    }

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            _authState.update { it.copy(isLoading = true, error = null) }

            val result = repository.signInWithGoogle(idToken)

            result.onSuccess { user ->
                _authState.update { it.copy(user = user, isLoading = false) }
            }.onFailure { e ->
                _authState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    suspend fun sendOTP(
        phoneNumber: String,
        callback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        _authState.value = _authState.value.copy(
            isLoading = true,
            error = null
        )
        repository.sendOTP(phoneNumber, callback)
    }

    fun signInWithPhone(verificationId: String, otp: String) {
        viewModelScope.launch {
            _authState.update { it.copy(isLoading = true, error = null) }

            val result = repository.verifyOtp(verificationId, otp)

            result.onSuccess { user ->
                _authState.update { it.copy(user = user, isLoading = false) }
            }.onFailure { e ->
                _authState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun logout(){
        repository.logout()
        _authState.value = AuthState()
    }
}