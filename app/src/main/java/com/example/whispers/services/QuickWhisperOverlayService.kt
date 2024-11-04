package com.example.whispers.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.view.WindowManager
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.getSystemService
import com.example.whispers.ui.pages.QuickWhisperButton

class QuickWhisperOverlayService : Service() {

    private lateinit var windowManager: WindowManager
    private lateinit var overlayView: ComposeView

    override fun onCreate() {
        super.onCreate()

        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        overlayView = ComposeView(this).apply {
            setContent {
                QuickWhisperButton()
            }
        }

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            android.graphics.PixelFormat.TRANSLUCENT
        )

        params.x = 100
        params.y = 100
        windowManager.addView(overlayView, params)
    }

    override fun onDestroy() {
        super.onDestroy()
        windowManager.removeView(overlayView)
    }
    override fun onBind(intent: Intent?): IBinder? = null

}
