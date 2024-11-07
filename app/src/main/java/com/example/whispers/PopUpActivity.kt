package com.example.whispers

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.MotionEvent
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.example.whispers.objects.dumbWhispers
import com.example.whispers.services.SharedPrefs
import com.example.whispers.services.Utility
import com.example.whispers.ui.theme.WhispersTheme
import java.time.LocalDate
import java.time.LocalTime
import kotlin.math.roundToInt
import kotlin.system.exitProcess

class PopUpActivity : ComponentActivity() {

    private var isPermissionGranted by mutableStateOf(false)
    private var showPopUp by mutableStateOf(false)

    val overlayPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        isPermissionGranted = Settings.canDrawOverlays(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isPermissionGranted = Settings.canDrawOverlays(this)

        setContent {
            if (isPermissionGranted) {
                val context = LocalContext.current
                val windowManager = context.getSystemService(Context.WINDOW_SERVICE)

                WhispersTheme {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                finishAffinity()
                                exitProcess(0)
                            }
                    ) {
                        AddFloatingButtonOverlay(windowManager = windowManager as WindowManager)
                    }
                }



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
        FloatingActionButton(onClick = { }) {
            Text(text = "works")
        }
    }

    @Composable
    fun AddFloatingButtonOverlay(
        windowManager: WindowManager
    ) {
        val context = LocalContext.current

        val whisperList = remember {
            mutableStateListOf<dumbWhispers>()
        }
        LaunchedEffect(Unit) {
            val loadedWhispers = SharedPrefs().getWhispersFromPrefs(context)
            whisperList.clear()
            whisperList.addAll(loadedWhispers)
        }

        val composeView = remember {
            ComposeView(context).apply {
                setContent {
                    FloatingBox(
                        onDismiss = {
                            finishAffinity()
                            exitProcess(0)
                        },
                        onClick = { newWhisper ->
                            whisperList.add(
                                dumbWhispers(
                                    text = newWhisper.text,
                                    createdBy = newWhisper.createdBy
                                )
                            )
                            SharedPrefs().saveWhispersToPrefs(context, whisperList)
                            finishAffinity()
                            exitProcess(0)
                        }
                    )
                }
            }
        }

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
            android.graphics.PixelFormat.TRANSLUCENT
        )

        params.x = 100
        params.y = 100
        composeView.setViewTreeLifecycleOwner(LocalLifecycleOwner.current)
        composeView.setViewTreeSavedStateRegistryOwner(LocalSavedStateRegistryOwner.current)

        windowManager.addView(composeView, params)
    }

    @Composable
    fun FloatingBox(
        onClick: (dumbWhispers) -> Unit,
        onDismiss: () -> Unit
    ) {
        var whisper by remember {
            mutableStateOf("")
        }

        val context = LocalContext.current
        var offsetX by remember { mutableFloatStateOf(0f) }
        var offsetY by remember { mutableFloatStateOf(0f) }

        Box(
            modifier = Modifier
                .offset { (IntOffset(offsetX.roundToInt(), offsetY.roundToInt())) }
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    }
                }
                .wrapContentSize()
                .background(Color.White, shape = RoundedCornerShape(8.dp))
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
                    label = { Text("whisper") }
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Button(
                    onClick = {
                        if (whisper.isNotBlank()) {
                            onClick(
                                dumbWhispers(
                                    text = whisper,
                                    createdBy = (LocalTime.now().toString()).substring(0, 5)
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
