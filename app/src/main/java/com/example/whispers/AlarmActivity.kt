package com.example.whispers

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.whispers.objects.dumbWhispers
import com.example.whispers.services.AlarmReceiver
import com.example.whispers.services.SharedPrefs

class AlarmActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlarmScreen(onDismiss = {
                AlarmReceiver.ringtone?.stop()
                finishAffinity()
            })
        }

        window.insetsController?.hide(WindowInsets.Type.statusBars())
    }
}

@Composable
fun AlarmScreen(
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val whisperList = remember { mutableStateListOf<dumbWhispers>() }
    LaunchedEffect(Unit) {
        val loadedWhispers = SharedPrefs().getWhispersFromPrefs(context)
        whisperList.clear()
        whisperList.addAll(loadedWhispers)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF7D786)
                )
            ) {
                Text(
                    text = "All done",
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                itemsIndexed(whisperList) { index, item ->
                    Text(text = item.text)
                    Text(text = item.createdBy)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    AlarmReceiver.ringtone?.stop()
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF7D786)
                )
            ) {
                Text(
                    text = "See in app",
                    color = Color.Black
                )
            }
        }
    }
}
