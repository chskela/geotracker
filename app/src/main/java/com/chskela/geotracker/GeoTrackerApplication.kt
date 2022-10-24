package com.chskela.geotracker

import android.app.Application
import com.chskela.geotracker.tracker.data.AppDatabase
import com.chskela.geotracker.tracker.data.LocationRepository

class GeoTrackerApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { LocationRepository(database.locationDao()) }
}