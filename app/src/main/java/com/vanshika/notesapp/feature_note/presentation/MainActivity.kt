package com.vanshika.notesapp.feature_note.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.auth.FirebaseAuth
import com.vanshika.notesapp.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.vanshika.notesapp.feature_note.presentation.authentication.AuthScreen
import com.vanshika.notesapp.feature_note.presentation.authentication.Viewmodel
import com.vanshika.notesapp.feature_note.presentation.notes.NotesScreen
import com.vanshika.notesapp.feature_note.presentation.profile.ProfileScreen
import com.vanshika.notesapp.feature_note.presentation.util.Screen
import com.vanshika.notesapp.ui.theme.NotesAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewmodel: Viewmodel = hiltViewModel()
            val firebaseAuth = FirebaseAuth.getInstance()
            var startDestination by remember { mutableStateOf(Screen.AuthScreen.route) }

            DisposableEffect(Unit) {
                val listener = FirebaseAuth.AuthStateListener { auth ->
                    startDestination = if (auth.currentUser != null) {
                        Screen.NoteScreen.route
                    } else {
                        Screen.AuthScreen.route
                    }
                }
                firebaseAuth.addAuthStateListener(listener)
                onDispose {
                    firebaseAuth.removeAuthStateListener(listener)
                }
            }

            NotesAppTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController,
                        startDestination = startDestination
                    ){
                        composable(route = Screen.NoteScreen.route){
                            NotesScreen(
                                navController=navController,
                                viewmodel=viewmodel
                            )
                        }
                        composable(route = Screen.AddEditNoteScreen.route+
                                "?noteId={noteId}&noteColor={noteColor}",
                            arguments = listOf(
                                navArgument("noteId"){
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument("noteColor"){
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ){ backStackEntry ->
                            val color = backStackEntry.arguments?.getInt("noteColor") ?: -1
                            AddEditNoteScreen(
                                navController = navController,
                                noteColor = color
                            )
                        }
                        composable(route = Screen.AuthScreen.route){
                            AuthScreen(
                                viewModel = viewmodel,
                                navController = navController
                            )
                        }
                        composable(route = Screen.ProfileScreen.route){
                            ProfileScreen()
                        }
                    }
                }
            }
        }
    }
}