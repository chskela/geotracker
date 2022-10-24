package com.chskela.geotracker.tracker.data

class LocationRepository(private val locationDao: LocationDao) {

    fun save(location: Location) {
        locationDao.save(location)
    }

    fun getListLocationNotSend(): List<Location> = locationDao.getListLocationNotSend()
}