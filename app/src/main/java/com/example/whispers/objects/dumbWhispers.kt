package com.example.whispers.objects

import com.example.whispers.R

data class dumbWhispers(
    val text: String = "this is a whisper",
    val createdBy: String = "27/10/24",
    val aiSummary: String = "about work",
    val iconWhisper: Int = R.drawable.ic_launcher_foreground,
)
