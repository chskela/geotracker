package com.chskela.geotracker.tracker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LocationDao {

    @Insert
    fun save(location: Location)

    @Query("SELECT * FROM location WHERE is_send = 0")
    fun getListLocationNotSend() : List<Location>
}