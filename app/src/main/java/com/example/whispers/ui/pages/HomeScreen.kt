package com.example.whispers.ui.pages

import android.graphics.Paint.Align
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.example.whispers.objects.dumbWhispers

fun createDumbWhispers(): List<dumbWhispers> {
    val dumbWhispers = mutableListOf<dumbWhispers>()
    for (i in 1..20) {
        dumbWhispers.add(dumbWhispers())
    }
    return dumbWhispers
}

@Composable
fun HomeScreen() {

    Box(
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(40.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DateCol()
            WhispersCol()
        }
//        BottomNav()
    }
}

@Composable
fun DateCol() {
    Column (
        modifier = Modifier
            .padding(bottom = 20.dp)
    ) {
        Text(text = "26/10/2024")
    }
}

@Composable
fun WhispersCol() {
    val dumbWhispers = createDumbWhispers()

    LazyColumn {
        itemsIndexed (dumbWhispers) {index, item ->
            WhisperCard(text = item.text, createdAt = item.createdBy, aiSummary = item.aiSummary)
        }
    }
}

@Composable
fun WhisperCard(
    text: String,
    createdAt: String,
    aiSummary: String,
) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(10.dp)
    ) {
        Column (
            modifier = Modifier
                .padding(10.dp)
        ) {
            Text(text = text)

            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = aiSummary)
                Text(text = createdAt)
            }
        }
    }
}

//@Composable
//fun BottomNav() {
//    Column {
//        Text(text = "Bottom Nav here")
//    }
//}

