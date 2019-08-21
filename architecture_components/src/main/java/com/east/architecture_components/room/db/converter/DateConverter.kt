package com.east.architecture_components.room.db.converter

import androidx.room.TypeConverter
import java.util.*

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:
 *  @author: East
 *  @date: 2019-08-08
 * |---------------------------------------------------------------------------------------------------------------|
 */
object DateConverter {

    @JvmStatic
    @TypeConverter
    fun toDate(timestamp:Long?) : Date? {
        return if(timestamp==null) null else Date(timestamp)
    }

    @JvmStatic
    @TypeConverter
    fun toTimestamp(date:Date?):Long?{
        return if(date == null) null else date.time
    }
}