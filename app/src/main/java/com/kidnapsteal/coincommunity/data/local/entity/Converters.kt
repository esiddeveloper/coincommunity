package com.kidnapsteal.coincommunity.data.local.entity

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun dateToTimestamp(date: Date): Long = date.time

    @TypeConverter
    fun timestampToDate(value: Long): Date = Date(value)

}