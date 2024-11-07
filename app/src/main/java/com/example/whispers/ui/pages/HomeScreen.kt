package com.example.whispers.ui.pages

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.content.getSystemService
import com.example.whispers.R
import com.example.whispers.objects.BottomMenuContent
import com.example.whispers.objects.dumbWhispers
import com.example.whispers.services.SharedPrefs
import com.example.whispers.services.Utility
import java.time.LocalDate
import java.time.LocalTime

//val whisperList = mutableStateListOf<dumbWhispers>()

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun HomeScreen(
    showDialog: MutableState<Boolean> = mutableStateOf(false),
    onFinishAffinity: () -> Unit
) {
    val context = LocalContext.current
    val whisperList = remember { mutableStateListOf<dumbWhispers>() }
    LaunchedEffect(Unit) {
        val loadedWhispers = SharedPrefs().getWhispersFromPrefs(context)
        whisperList.clear()
        whisperList.addAll(loadedWhispers)
    }

    var showDialogNow: MutableState<Boolean>

    if (showDialog.value) {
        showDialogNow = remember {
            mutableStateOf(true)
        }
    } else {
        showDialogNow = remember {
            mutableStateOf(false)
        }
    }

    Box(
        modifier = Modifier
            .background(Color(0XFFFFFFF7))
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(40.dp)
                .fillMaxSize(),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DateCol()
            WhispersCol(
                whisperList
            )
        }
        BottomNav(
            items = listOf(
                BottomMenuContent("User", R.drawable.ic_launcher_foreground),
                BottomMenuContent("Add", R.drawable.ic_launcher_foreground)
            ),
            modifier = Modifier.align(Alignment.BottomCenter),
            onShowDialogChange = { show ->
                showDialogNow.value = show
            },
            onFinishAffinity = onFinishAffinity
        )

        if (showDialogNow.value) {
            AddWhisperDialog(
                onDismiss = {
                    showDialogNow.value = false
                },
                onAddNote = {newWhisper ->
                    whisperList.add(dumbWhispers(
                        text = newWhisper.text,
                        createdBy = newWhisper.createdBy
                    ))
                    SharedPrefs().saveWhispersToPrefs(context, whisperList)
                    showDialogNow.value = false
                }
            )
        }

    }
}

@Composable
fun DateCol() {
    Column (
        modifier = Modifier
            .padding(bottom = 20.dp)
            .clickable { }
            .border(1.dp, color = Color(0xFFF7D786))
    ) {
        Text(
            text = LocalDate.now().toString(),
        )
    }
}

@Composable
fun WhispersCol(
    whisperList: MutableList<dumbWhispers>
) {

    val showWhisperDetail by remember {
        mutableStateOf(false)
    }

    LazyColumn {
        itemsIndexed (whisperList) {index, item ->
            WhisperCard (
                text = item.text,
                createdAt = item.createdBy,
                onItemClick = {
                    WhisperDetails(text = item.text, createdAt = item.createdBy, onDismiss = {
                    })
                }
            )
        }
    }
}

@Composable
fun WhisperDetails(
    text: String,
    createdAt: String,
    onDismiss: () -> Unit
) {
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
                    text = text
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(text = createdAt)

                Spacer(
                    modifier = Modifier.height(8.dp)
                )
            }
        }
    }
}

@Composable
fun WhisperCard(
    text: String,
    createdAt: String,
    onItemClick: @Composable () -> Unit
) {

    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(70.dp)
            .background(Color(0xFFF7D786))
            .clickable { onItemClick },
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column (
            modifier = Modifier
                .background(Color(0xFFF7D786))
                .padding(15.dp)
                .fillMaxWidth()
        ) {
            Text(text = text)

            Row (
                modifier = Modifier
                    .fillMaxWidth()
//                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = createdAt,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun BottomNav(
    items: List<BottomMenuContent>,
    modifier: Modifier = Modifier,
    onShowDialogChange: (Boolean) -> Unit,
    onFinishAffinity: () -> Unit
) {
    val context = LocalContext.current
    Row (
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFF7D786))
            .padding(10.dp)
    ) {
        items.forEachIndexed {index, item ->
            BottomMenuItem(item = item) {
                if (item.title == "Add") {
                    onShowDialogChange(true)
                }

                if (item.title == "User") {
                    if (isIgnoringBatteryOptimizations(context)) {
                        val triggerTime = System.currentTimeMillis() + 10 * 1000L
                        Utility().scheduleAlarm(context, triggerTime)
                        onFinishAffinity()
                    } else {
                        requestBatteryOptimizationExclusion(context)
                    }
                }
            }
        }
    }
}


@Composable
fun BottomMenuItem(
    item: BottomMenuContent,
    onItemClick: () -> Unit
) {

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable {
            onItemClick()
        }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .padding(5.dp)
        ) {
            Icon(
                painter = painterResource(id = item.iconId),
                contentDescription = item.title,
                modifier = Modifier.size(20.dp)
            )
        }
        Text(text = item.title)
    }
}


/// for scheduling alarms for now -- for the mvp feature
fun requestBatteryOptimizationExclusion(context: Context) {
    if (!isIgnoringBatteryOptimizations(context)) {
        val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
            .setData(Uri.parse("package:${context.packageName}"))
        context.startActivity(intent)
    }
}

fun isIgnoringBatteryOptimizations(context: Context): Boolean {
    val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
    return powerManager.isIgnoringBatteryOptimizations(context.packageName)
}