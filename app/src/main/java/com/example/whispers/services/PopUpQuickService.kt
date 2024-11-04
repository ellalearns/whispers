package com.example.whispers.services

import android.app.PendingIntent
import android.content.Intent
import android.service.quicksettings.TileService
import com.example.whispers.PopUpActivity

class PopUpQuickService : TileService() {
    override fun onTileAdded() {
        super.onTileAdded()
        qsTile.label = "Quick Whisper"
        qsTile.contentDescription = qsTile.label
        qsTile.updateTile()
    }

    override fun onTileRemoved() {
        super.onTileRemoved()
    }

    override fun onClick() {
        super.onClick()

//        val intent = Intent(this, PopUpActivity::class.java)
//        startActivityAndCollapse(
//            PendingIntent.getActivity(
//                this,
//                0,
//                intent,
//                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//            )
//        )

        val overlayIntent = Intent(this, QuickWhisperOverlayService::class.java)
        startService(overlayIntent)
//        goHome()
    }
}
