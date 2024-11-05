package com.example.whispers.ui.pages


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.whispers.objects.dumbWhispers
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun AddWhisperDialog(
    onDismiss: () -> Unit,
    onAddNote: (dumbWhispers) -> Unit
) {
    var whisper by remember {
        mutableStateOf("")
    }

    Dialog(onDismissRequest = { onDismiss() }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Drop a Whisper",
                    style = MaterialTheme.typography.titleSmall
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                TextField(
                    value = whisper,
                    onValueChange = { whisper = it },
                    label = { Text("whisper") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Button(
                    onClick = {
                        if (whisper.isNotBlank()) {
                            onAddNote(
                                dumbWhispers(
                                text = whisper,
                                createdBy = LocalTime.now().toString()
                            )
                            )
                            onDismiss()
                        }
                    }
                ) {
                    Text("whispeeerrr")
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QS_AddWhisperDialog(
    onAdd: (dumbWhispers) -> Unit,
    onDismiss: () -> Unit
) {
    var whisper by remember {
        mutableStateOf("")
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "whisper") },
        text = {
               OutlinedTextField (
                   value = whisper,
                   onValueChange = {
                       whisper = it
                   },
                   placeholder = {
                       Text("whisper")
                   }
               )
        },
        confirmButton = {
                        Button (
                            onClick = {
                                onAdd(
                                    dumbWhispers(
                                    text = whisper,
                                    createdBy = LocalTime.now().toString()
                                )
                                )
                                onDismiss()
                            }) {
                            Text(text = "whisper")
                        }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(text = "cancel")
            }
        }
    )

}


@Composable
fun QuickWhisperButton(
    onClick: () -> Unit = {}
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = Modifier.size(48.dp)
    ) {
        Text(text = "FABULOUS")
    }
}
