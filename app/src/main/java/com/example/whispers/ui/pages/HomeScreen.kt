package com.example.whispers.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.whispers.R
import com.example.whispers.objects.BottomMenuContent
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
        BottomNav(
            items = listOf(
                BottomMenuContent("User", R.drawable.ic_launcher_foreground),
                BottomMenuContent("Add", R.drawable.ic_launcher_foreground)
            ),
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun DateCol() {
    Column (
        modifier = Modifier
            .padding(bottom = 20.dp)
            .clickable { }
            .border(1.dp, color = Color.Cyan)
    ) {
        Text(
            text = "26/10/2024",
        )
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
            .padding(10.dp)
            .height(80.dp)
            .clickable {
            }
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

@Composable
fun BottomNav(
    items: List<BottomMenuContent>,
    modifier: Modifier = Modifier,
) {
    Row (
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Gray)
            .padding(10.dp)
    ) {
        items.forEachIndexed {index, item ->
            BottomMenuItem(item = item) {
                
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

