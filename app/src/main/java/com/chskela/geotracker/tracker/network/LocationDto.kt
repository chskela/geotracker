package com.chskela.geotracker.tracker.network

data class LocationDto(
    val uid: Long,
    val uidPhone: String,
    val date: String,
    val latitude: Double,
    val longitude: Double,
)
