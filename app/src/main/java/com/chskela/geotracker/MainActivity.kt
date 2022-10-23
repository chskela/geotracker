package com.chskela.geotracker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.chskela.geotracker.tracker.presentation.MainScreen
import com.chskela.geotracker.tracker.presentation.MainScreenViewModel
import com.chskela.geotracker.tracker.service.LocationService
import com.chskela.geotracker.ui.theme.GeoTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(
            NotificationChannel(
                LocationService.CHANNEL_ID, "location", NotificationManager.IMPORTANCE_LOW
            )
        )
//        onRequestPermissionsResult()
        val mainScreenViewModel: MainScreenViewModel by viewModels()
        setContent {
            GeoTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(mainScreenViewModel)
                }
            }
        }
    }
}
