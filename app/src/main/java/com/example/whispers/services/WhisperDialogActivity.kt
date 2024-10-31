package com.example.whispers.services

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.whispers.ui.pages.QS_AddWhisperDialog
import com.example.whispers.ui.pages.whisperList

class WhisperDialogActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QS_AddWhisperDialog(
                onAdd = { noteText ->
                    whisperList.add(noteText)
                    Toast.makeText(this, "heard :)", Toast.LENGTH_SHORT).show()
                },
                onDismiss = { finish() }
            )
        }
    }
}
