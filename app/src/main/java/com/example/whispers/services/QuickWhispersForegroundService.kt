package com.example.whispers.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.view.Gravity
import android.view.WindowManager
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.example.whispers.R
import com.example.whispers.PopUpActivity
//import androidx.savedstate.SavedStateRegistry

class QuickWhispersForegroundService() : Service(), LifecycleOwner {

    private lateinit var windowManager: WindowManager
    private lateinit var composeView: ComposeView

    private val lifecycleRegistry = LifecycleRegistry(this)
//    private override val savedStateRegistry = SavedStateRegistry()
    override val lifecycle: Lifecycle
        get() = lifecycleRegistry

    override fun onCreate() {
        super.onCreate()
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
        createNotificationChannel()
        startForeground(1, getNotification())

        if (Settings.canDrawOverlays(this)) {
            setUpOverlay()
        } else {
            stopSelf() //later change to also get permission
            val intent = Intent(this, PopUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setUpOverlay() {
//        val viewModel = ViewModelProvider(this, SavedStateViewModelFactory(this, this)).get(WhisperViewModel::class.java)
        composeView = ComposeView(this).apply {
            setViewTreeLifecycleOwner(this@QuickWhispersForegroundService)
            setContent {
                OverlayContent()
            }
        }

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        )
        params.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL

        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.addView(composeView, params)
    }

    @Composable
    fun OverlayContent() {
        var whisper by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .background(Color.White.copy(alpha = 0.9f))
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                value = whisper,
                onValueChange = {
                    whisper = it
//                    viewModel.whisper = it
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                if (whisper.isNotBlank()) {
                    Toast.makeText(this@QuickWhispersForegroundService, "new whisper: $whisper", Toast.LENGTH_SHORT).show()
                    whisper = ""
//                    viewModel.clearWhisper()
                } else {
                    Toast.makeText(this@QuickWhispersForegroundService, "please enter a whisper", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text(text = "drop whisper")
            }
        }
    }

    private fun getNotification(): Notification {
        return NotificationCompat.Builder(this, "QuickWhisperChannel")
            .setContentTitle("Drop Whisper")
            .setContentText("drop a quick whisper")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            "QuickWhisperChannel",
            "Quick Whisper Channel",
            NotificationManager.IMPORTANCE_LOW
        )
        val manager = getSystemService(NotificationManager::class.java)

        manager.createNotificationChannel(channel)
    }

    override fun onDestroy() {
        super.onDestroy()
        windowManager.removeView(composeView)
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
    }
    override fun onBind(intent: Intent?): IBinder? = null

}
