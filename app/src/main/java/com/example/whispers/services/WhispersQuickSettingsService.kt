package com.example.whispers.services

import android.app.PendingIntent
import android.content.Intent
import android.service.quicksettings.TileService
import android.util.Log
import com.example.whispers.WhisperQSActivity

class WhispersQuickSettingsService : TileService() {

    var counter = 0
    override fun onClick() {
        super.onClick()
        counter++
        qsTile.label = counter.toString()
        qsTile.contentDescription = qsTile.label
        qsTile.updateTile()

        val intent = Intent(this, WhisperQSActivity::class.java)
        startActivityAndCollapse(
            PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun onTileAdded() {
        super.onTileAdded()
    }

    override fun onTileRemoved() {
        super.onTileRemoved()
    }

    override fun onStartListening() {
        super.onStartListening()
    }

    override fun onStopListening() {
        super.onStopListening()
    }
}
