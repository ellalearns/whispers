package com.example.whispers.services

import android.content.Intent
import android.service.quicksettings.TileService

class AddNoteTileService: TileService() {
    override fun onTileAdded() {
        qsTile.label = "Add Whisper"
        qsTile.updateTile()
    }

    override fun onClick() {
        showAddWhisperDialog()
    }

    private fun showAddWhisperDialog() {
        val dialogIntent = Intent(this, WhisperDialogActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(dialogIntent)
    }
}
