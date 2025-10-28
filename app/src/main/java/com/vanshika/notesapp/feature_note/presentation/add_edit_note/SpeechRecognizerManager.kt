package com.vanshika.notesapp.feature_note.presentation.add_edit_note

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer

class SpeechRecognizerManager(
    context: Context,
    private val onResult: (String) -> Unit,
    private val onError: (String) -> Unit,
    private val onListeningStateChanged: (Boolean) -> Unit
) {
    private var speechRecognizer: SpeechRecognizer? = null
    private var recognizerIntent: Intent? = null

    init {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
        speechRecognizer?.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(p0: Bundle?) {
                onListeningStateChanged(true)
            }
            override fun onBeginningOfSpeech() {
                onListeningStateChanged(true)
            }
            override fun onRmsChanged(p0: Float) {}
            override fun onBufferReceived(p0: ByteArray?) {}
            override fun onEndOfSpeech() {
                onListeningStateChanged(false)
            }

            override fun onError(p0: Int) {
                onListeningStateChanged(false)
                onError("Speech recognition error: $p0")
            }

            override fun onResults(p0: Bundle?) {
                onListeningStateChanged(false)
                val results = p0?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                results?.firstOrNull()?.let {
                    onResult(it)
                }
            }

            override fun onPartialResults(p0: Bundle?) {}
            override fun onEvent(p0: Int, p1: Bundle?) {}
        })
        recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, java.util.Locale.getDefault())
        }
    }
    fun startListening(){
        speechRecognizer?.startListening(recognizerIntent)
    }
    fun stopListening(){
        speechRecognizer?.stopListening()
    }
    fun destroy(){
        speechRecognizer?.destroy()
    }
}