package com.chskela.geotracker.tracker.service

import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.icu.util.Calendar
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.chskela.geotracker.GeoTrackerApplication
import com.chskela.geotracker.R
import com.chskela.geotracker.tracker.data.Location
import com.chskela.geotracker.tracker.data.LocationRepository
import com.chskela.geotracker.tracker.location.DefaultLocationClient
import com.chskela.geotracker.tracker.location.LocationClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.SimpleDateFormat
import java.util.*

class LocationService : Service() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var locationClient: LocationClient
    private lateinit var repository: LocationRepository

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        repository = (application as GeoTrackerApplication).repository
        locationClient = DefaultLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun stop() {
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun start() {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Tracking location")
            .setContentText("Location: null")
            .setSmallIcon(R.drawable.icon)
            .setOngoing(true)

        val notificationManager = getSystemService(NotificationManager::class.java)

        locationClient.getLocationUpdate(5000L)
            .catch { e -> e.printStackTrace() }
            .onEach { location ->
                val dateTime = formatDate(Calendar.getInstance().time)
                val latitude = location.latitude
                val longitude = location.longitude
                val candidate = Location(
                    uidPhone = "test",
                    date = dateTime,
                    latitude = latitude,
                    longitude = longitude
                )
                repository.save(candidate)

                val updateNotification = notification.setContentText(
                    "Location: $latitude, $longitude"
                )
                notificationManager.notify(NOTIFICATION_ID, updateNotification.build())
            }
            .launchIn(serviceScope)

        startForeground(NOTIFICATION_ID, notification.build())
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    private fun formatDate(date: Date) =
        SimpleDateFormat("yyyyMMddkkmmss", Locale.getDefault()).format(date)

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
        const val CHANNEL_ID = "LOCATION"
        const val NOTIFICATION_ID = 1
    }
}