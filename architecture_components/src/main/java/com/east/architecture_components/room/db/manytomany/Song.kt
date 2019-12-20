package com.east.architecture_components.room.db.manytomany

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:
 *  @author: East
 *  @date: 2019-08-06
 * |---------------------------------------------------------------------------------------------------------------|
 */
@Entity(tableName = "song")
data class Song(
    @PrimaryKey var id: Int,
    @ColumnInfo(name = "song_name") val songName: String?,
    @ColumnInfo(name = "artist_name")val artistName: String?
){
    override fun toString(): String {
        return "$id--$songName--$artistName"
    }
}