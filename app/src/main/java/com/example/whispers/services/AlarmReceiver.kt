package com.example.whispers.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import com.example.whispers.AlarmActivity

class AlarmReceiver: BroadcastReceiver() {

    companion object {
        var ringtone: Ringtone? = null
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        //play alarm ringtone
        val alarmUri : Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        ringtone = RingtoneManager.getRingtone(context, alarmUri)
        ringtone?.play()

        //show alarm content
        val alarmIntent = Intent(context, AlarmActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        context?.startActivity(alarmIntent)
    }
}
