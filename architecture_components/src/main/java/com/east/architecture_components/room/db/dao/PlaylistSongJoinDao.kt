package com.east.architecture_components.room.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomWarnings
import com.east.architecture_components.room.db.manytomany.Playlist
import com.east.architecture_components.room.db.manytomany.PlaylistSongJoin
import com.east.architecture_components.room.db.manytomany.Song

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:  多对多 查询
 *  @author: East
 *  @date: 2019-08-06
 * |---------------------------------------------------------------------------------------------------------------|
 */
@Dao
interface PlaylistSongJoinDao {


    @Insert
    fun insertPlayList(vararg playList : Playlist)

    @Query("select * from playlist")
    fun queryPlayList() :LiveData<List<Playlist>>

    @Insert
    fun insertSong(vararg song : Song)

    @Query("select * from song")
    fun querySong() :LiveData<List<Song>>

    @Insert
    fun insert(vararg playlistSongJoin: PlaylistSongJoin)

    @Query("""
           SELECT * FROM playlist
           INNER JOIN playlist_song_join
           ON playlist.id=playlist_song_join.playlistId
           WHERE playlist_song_join.songId=:songId
           """)
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    fun getPlaylistsForSong(songId: Int): LiveData<List<Playlist>>

    @Query("""
           SELECT * FROM song
           INNER JOIN playlist_song_join
           ON song.id=playlist_song_join.songId
           WHERE playlist_song_join.playlistId=:playlistId
           """)
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    fun getSongsForPlaylist(playlistId: Int): LiveData<List<Song>>
}