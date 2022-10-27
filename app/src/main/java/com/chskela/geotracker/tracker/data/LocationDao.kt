package com.chskela.geotracker.tracker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface LocationDao {

    @Insert
    fun save(location: Location)

    @Update
    fun update(vararg locations: Location)

    @Query("SELECT * FROM location WHERE is_send = 0")
    fun getListLocationNotSend() : List<Location>
}