package com.chskela.geotracker.tracker.data.mapper

import com.chskela.geotracker.tracker.data.Location
import com.chskela.geotracker.tracker.network.LocationDto

fun Location.toLocationDto() = LocationDto(
    uid = uid ?: 0,
    uidPhone = uidPhone,
    date = date,
    latitude = latitude,
    longitude = longitude
)