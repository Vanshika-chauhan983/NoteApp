package com.vanshika.notesapp.feature_note.presentation.authentication

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.vanshika.notesapp.feature_note.presentation.util.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(
    viewModel: Viewmodel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val authState by viewModel.authState.collectAsState()
    val authService = remember { AuthService(context, viewModel) }

    var otp by remember { mutableStateOf("") }
    var showOtpField by remember { mutableStateOf(false) }
    var phoneNumber by remember { mutableStateOf("") }

    val initialTime = 120
    var ticks by remember { mutableIntStateOf(initialTime) }
    var isTimerRunning by remember { mutableStateOf(false) }

    // Google launcher
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        authService.googleSignInResult(result.data)
    }

    LaunchedEffect(authState.user) {
        if (authState.user != null) {
            navController.navigate(Screen.NoteScreen.route) {
                popUpTo(Screen.AuthScreen.route) { inclusive = true }
            }
        }
    }

    LaunchedEffect(authState.isCodeSent) {
        if (authState.isCodeSent) {
            showOtpField = true
            ticks = initialTime
            isTimerRunning = true
            while (ticks > 0) {
                delay(1000L)
                ticks--
            }
            isTimerRunning = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            if (!showOtpField) "Sign In" else "Enter OTP",
            style = MaterialTheme.typography.headlineLarge.copy(
                color = MaterialTheme.colorScheme.onSurface
            ),
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(32.dp))

        if (!showOtpField) {
            PhoneInputSection(
                phoneNumber, { phoneNumber = it }, !authState.isLoading
            )

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = {
                    if (phoneNumber.isNotBlank()) {
                        scope.launch {
                            viewModel.sendOTP("+91${phoneNumber}", authService.callbacks)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Text("Send OTP", color = MaterialTheme.colorScheme.onSecondaryContainer)
            }
        } else {
            OtpInputSection(otp, { otp = it }, !authState.isLoading)

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = {
                    viewModel.signInWithPhone(authState.verificationId, otp)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Verify OTP")
            }

            Spacer(Modifier.height(8.dp))

            // Resend OTP or show countdown
            if (isTimerRunning) {
                Text("Resend available in $ticks seconds")
            } else {
                TextButton(
                    onClick = {
                        scope.launch {
                            viewModel.sendOTP("+91${authState.phoneNumber}", authService.callbacks)
                        }
                    }
                ) {
                    Text("Resend OTP")
                }
            }
        }

        Spacer(Modifier.height(32.dp))

        Text("Or", color = MaterialTheme.colorScheme.onSurface)

        Spacer(Modifier.height(24.dp))

        // Google Sign-In
        Button(
            onClick = { launcher.launch(authService.googleSignInClient.signInIntent) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Icon(
                Icons.Default.AccountCircle,
                contentDescription = "Google",
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Spacer(Modifier.width(8.dp))
            Text("Sign in with Google", color = MaterialTheme.colorScheme.onSecondaryContainer)
        }
    }
}

@Composable
fun PhoneInputSection(
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    enabled: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp)
        ) {
            Text("+91", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Icon(
                Icons.Default.Phone,
                "Phone",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(20.dp)
            )
            Spacer(Modifier.width(8.dp))
            VerticalDivider(
                modifier = Modifier.height(28.dp),
                color = MaterialTheme.colorScheme.outline
            )
            TextField(
                value = phoneNumber,
                onValueChange = { newText ->
                    if (newText.length <= 10 && newText.all { it.isDigit() }) {
                        onPhoneNumberChange(newText)
                    }
                },
                placeholder = { Text("Phone number") },
                enabled = enabled,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Composable
fun OtpInputSection(otp: String, onOtpChange: (String) -> Unit, enabled: Boolean) {
    OutlinedTextField(
        value = otp,
        onValueChange = { newText ->
            if (newText.length <= 6 && newText.all { it.isDigit() }) {
                onOtpChange(newText)
            }
        },
        label = { Text("6-Digit OTP") },
        enabled = enabled,
        singleLine = true,
        modifier = Modifier.fillMaxWidth(0.8f),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
    )
}