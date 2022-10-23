package com.chskela.geotracker.tracker.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat


fun checkIfPermissionGranted(context: Context, permission: String): Boolean {
    return (ActivityCompat.checkSelfPermission(context, permission)
            == PackageManager.PERMISSION_GRANTED)
}

fun shouldShowPermissionRationale(context: Context, permission: String): Boolean {
    val activity = context as Activity?
    return ActivityCompat.shouldShowRequestPermissionRationale(
        activity!!,
        permission
    )
}
