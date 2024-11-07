package com.example.whispers

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.whispers.objects.dumbWhispers
import com.example.whispers.ui.pages.AddWhisperDialog
import com.example.whispers.ui.pages.HomeScreen

class WhisperQSActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val showDialog = remember {
                mutableStateOf(true)
            }

            HomeScreen(
                showDialog = showDialog,
                onFinishAffinity = {
                    finishAffinity()
                }
            )
        }

    }
}

