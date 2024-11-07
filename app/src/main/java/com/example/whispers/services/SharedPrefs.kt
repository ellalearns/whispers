package com.example.whispers.services

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import com.example.whispers.objects.dumbWhispers
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPrefs {

    fun saveWhispersToPrefs(context: Context, whisperList: List<dumbWhispers>) {
        val sharedPreferences = context.getSharedPreferences("whispers_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(whisperList)
        editor.putString("whispers_key", json)
        editor.commit()
    }

    fun getWhispersFromPrefs(context: Context): List<dumbWhispers> {
        val sharedPreferences = context.getSharedPreferences("whispers_prefs", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("whispers_key", "[]")
        val gson = Gson()
        val type = object : TypeToken<List<dumbWhispers>>() {}.type
        return gson.fromJson(json, type)
    }
}
