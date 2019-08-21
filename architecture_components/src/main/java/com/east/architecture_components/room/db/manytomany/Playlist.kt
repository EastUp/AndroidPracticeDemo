package com.east.architecture_components.room.db.manytomany

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:
 *  @author: East
 *  @date: 2019-08-06
 * |---------------------------------------------------------------------------------------------------------------|
 */
@Entity(tableName = "playlist")
data class Playlist(
    @PrimaryKey var id: Int,
    val name: String?,
    val description: String?
){
    override fun toString(): String {
        return "$id---$name--$description"
    }
}