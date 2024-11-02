package com.example.whispers

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.whispers.objects.dumbWhispers
import com.example.whispers.ui.pages.AddWhisperDialog
import com.example.whispers.ui.pages.whisperList

class WhisperQSActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AddWhisperDialog(
                onDismiss = {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                },
                onAddNote = { newWhisper ->
                    whisperList.add(
                        dumbWhispers(
                            text = newWhisper.text,
                            createdBy = newWhisper.createdBy
                        )
                    )
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            )
        }
    }
}


