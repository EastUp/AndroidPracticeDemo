package com.east.architecture_components.room.db.manytomany

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:   多对多的中间表
 *  @author: East
 *  @date: 2019-08-06
 * |---------------------------------------------------------------------------------------------------------------|
 */
@Entity(tableName = "playlist_song_join",
    indices = arrayOf(Index(value = ["songId","playlistId"])),
    primaryKeys = arrayOf("playlistId","songId"),
    foreignKeys = arrayOf(
        ForeignKey(entity = Playlist::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("playlistId"),
            onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Song::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("songId"),
            onDelete = ForeignKey.CASCADE)
    )
)
data class PlaylistSongJoin(
    val playlistId: Int,
    val songId: Int
)