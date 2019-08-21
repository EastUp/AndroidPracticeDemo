package com.east.architecture_components.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.east.architecture_components.room.db.converter.DateConverter
import com.east.architecture_components.room.db.dao.BookDao
import com.east.architecture_components.room.db.dao.PlaylistSongJoinDao
import com.east.architecture_components.room.db.dao.UserBookDao
import com.east.architecture_components.room.db.dao.UserDao
import com.east.architecture_components.room.db.embedded.EmbeddedUser
import com.east.architecture_components.room.db.entity.RoomAutoKeyUser
import com.east.architecture_components.room.db.manytomany.Playlist
import com.east.architecture_components.room.db.manytomany.PlaylistSongJoin
import com.east.architecture_components.room.db.manytomany.Song
import com.east.architecture_components.room.db.onetomany.Book

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:  如果您的应用程序在单个进程中运行，则在实例化AppDatabase 对象时应遵循单例设计模式。
 *                 每个实例都相当昂贵，您很少需要在单个进程中访问多个实例。 RoomDatabase
 *                 通常，在整个APP中，只需要一个Room database实例。
 *  @author: East
 *  @date: 2019-08-03
 * |---------------------------------------------------------------------------------------------------------------|
 */
@Database(entities = [RoomAutoKeyUser::class, EmbeddedUser::class,
    Book::class, Playlist::class, PlaylistSongJoin::class, Song::class], version = 4)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun bookDao(): BookDao
    abstract fun userAndBookDao(): UserBookDao
    abstract fun playlistJoinDao(): PlaylistSongJoinDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(contenxt: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDataBase(contenxt).also { INSTANCE = it }
            }

        private fun buildDataBase(contenxt: Context): AppDatabase =
            Room.databaseBuilder(contenxt.applicationContext,
                AppDatabase::class.java, "room_database")
                //允许在主线程中查询...一般不推荐,因为数据库查询是一个耗时的操作
                .allowMainThreadQueries()
                .addMigrations(
                    MIGRATION_1_2,
                    MIGRATION_2_3,
                    MIGRATIOn_3_4
                )
                .build()

        //数据库迁移

        private var MIGRATION_1_2 = object :Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                //user表新增了索引  所以进行修改
                database.execSQL("create index index_user_uid on user(uid)")
            }
        }

        private var MIGRATION_2_3 = object :Migration(2,3){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("drop index index_user_uid ")
                // 创建临时表
                database.execSQL(
                    "CREATE TABLE if not exists users_new (uid INTEGER PRIMARY KEY autoincrement not null, first_name TEXT, last_name TEXT,date INTEGER)")
                database.execSQL("create index index_user_uid on users_new(uid)")
                // 拷贝数据
                database.execSQL(
                    "INSERT INTO users_new (uid, first_name, last_name) SELECT uid, first_name, last_name FROM user")
                // 删除老的
                database.execSQL("DROP TABLE user")
                // 改名
                database.execSQL("ALTER TABLE users_new RENAME TO user")
            }
        }

        private var MIGRATIOn_3_4 = object :Migration(3,4){
            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("alter table Playlist rename to playlist")
//                database.execSQL("alter table Song rename to song")
                database.execSQL("create table if not exists playlist (id integer primary key not null,name text,description text)")
                database.execSQL("drop table song")
                database.execSQL("create table if not exists song (id integer primary key not null,song_name text,artist_name text)")
                database.execSQL("drop table playlist_song_join")
                database.execSQL("CREATE TABLE IF NOT EXISTS playlist_song_join (playlistId INTEGER NOT NULL, songId INTEGER NOT NULL, PRIMARY KEY(playlistId, songId), FOREIGN KEY(playlistId) REFERENCES playlist(id) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(songId) REFERENCES song(id) ON UPDATE NO ACTION ON DELETE CASCADE )")
            }
        }


    }




}