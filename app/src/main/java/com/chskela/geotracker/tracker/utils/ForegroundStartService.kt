package com.chskela.geotracker.tracker.utils

import android.content.Context
import android.content.Intent
import com.chskela.geotracker.tracker.service.INTENT_COMMAND
import com.chskela.geotracker.tracker.service.MyService


fun Context.foregroundStartService(command: String) {
    val intent = Intent(this, MyService::class.java)
    if (command == "Start") {
        intent.putExtra(INTENT_COMMAND, command)

        startForegroundService(intent)

    } else if (command == "Exit") {
        intent.putExtra(INTENT_COMMAND, command)

        stopService(intent)
    }
}