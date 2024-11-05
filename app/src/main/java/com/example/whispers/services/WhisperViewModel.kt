package com.example.whispers.services

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class WhisperViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    var whisper: String
        get() = savedStateHandle.get<String>("whisper") ?: ""
        set(value) {
            savedStateHandle["whisper"] = value
        }

    fun clearWhisper() {
        whisper = ""
    }
}