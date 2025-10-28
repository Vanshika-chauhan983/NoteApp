package com.vanshika.notesapp.feature_note.data

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    fun saveAuthData(){

    }
}