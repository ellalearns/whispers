package com.example.whispers

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.example.whispers.ui.pages.HomeScreen

class PopUpActivity : ComponentActivity() {

    private var isPermissionGranted by mutableStateOf(false)
    private var showPopUp by mutableStateOf(false)

    private val overlayPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        isPermissionGranted = Settings.canDrawOverlays(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isPermissionGranted = Settings.canDrawOverlays(this)

        setContent {
            if (isPermissionGranted) {
//                PopUp()
                val context = LocalContext.current
                val windowManager = context.getSystemService(Context.WINDOW_SERVICE)

                AddFloatingButtonOverlay(windowManager = windowManager as WindowManager)

            } else {
                RequestOverlayPermission()
            }
        }
    }

    @Composable
    private fun RequestOverlayPermission() {
        LaunchedEffect(Unit) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            overlayPermissionLauncher.launch(intent)
        }
    }

    @Composable
    private fun PopUp() {
        FloatingActionButton(onClick = {  }) {
            Text(text = "works")
        }
    }

    @Composable
    fun AddFloatingButtonOverlay(
        windowManager: WindowManager
    ) {
        val context = LocalContext.current
        val composeView = remember { ComposeView(context).apply {
            setContent {
                FloatingButton {

                }
            }
        }}

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            android.graphics.PixelFormat.TRANSLUCENT)

        params.x = 100
        params.y = 100
        composeView.setViewTreeLifecycleOwner(LocalLifecycleOwner.current)
        composeView.setViewTreeSavedStateRegistryOwner(LocalSavedStateRegistryOwner.current)

        windowManager.addView(composeView, params)
    }

    @Composable
    fun FloatingButton(
        onClick: () -> Unit
    ) {
        FloatingActionButton(
            onClick = { onClick },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "floating overlay")
        }
    }
}
