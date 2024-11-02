package com.example.whispers.services

import android.content.Intent
import android.service.quicksettings.TileService
import android.util.Log

class WhispersQuickSettingsService : TileService() {

    var counter = 0
    override fun onClick() {
        super.onClick()
        counter++
        qsTile.label = counter.toString()
        qsTile.contentDescription = qsTile.label
        qsTile.updateTile()
    }

}