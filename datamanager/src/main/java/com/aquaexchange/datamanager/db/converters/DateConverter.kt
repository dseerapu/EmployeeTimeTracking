package com.aquaexchange.datamanager.db.converters

import androidx.room.TypeConverter
import java.util.*

/**
 * Converter used to convert date to long and store in DB and vice versa
 */
class DateConverter {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}