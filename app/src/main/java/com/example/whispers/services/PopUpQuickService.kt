package com.example.whispers.services

import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.service.quicksettings.TileService
import androidx.annotation.RequiresApi
import com.example.whispers.PopUpActivity

class PopUpQuickService : TileService() {
    override fun onTileAdded() {
        super.onTileAdded()
        qsTile.label = "Pop Up Whisper"
        qsTile.contentDescription = qsTile.label
        qsTile.updateTile()
    }

    override fun onTileRemoved() {
        super.onTileRemoved()
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onClick() {
        super.onClick()

        val intent = Intent(this, PopUpActivity::class.java)
        startActivityAndCollapse(
            PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}
