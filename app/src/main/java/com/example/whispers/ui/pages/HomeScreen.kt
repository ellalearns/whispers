package com.example.whispers.ui.pages

import android.graphics.Paint.Align
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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
    Column {
//        Text(text = "Whispers will be here")
        WhisperCard()
        WhisperCard()
        WhisperCard()
    }
}

@Composable
fun WhisperCard() {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(10.dp)
//            .fillMaxHeight(0.1f)
    ) {
        Text(text = "Whispers will be here")
    }
}

//@Composable
//fun BottomNav() {
//    Column {
//        Text(text = "Bottom Nav here")
//    }
//}

