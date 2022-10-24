package com.chskela.geotracker.tracker.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Location(
    @PrimaryKey(autoGenerate = true) val uid: Long? = null,

    @ColumnInfo(name = "uid_phone") val uidPhone: String,

    @ColumnInfo val date: String,

    @ColumnInfo val latitude: Long,

    @ColumnInfo val longitude: Long,

    @ColumnInfo(name = "is_send") val isSend: Boolean = false
)
