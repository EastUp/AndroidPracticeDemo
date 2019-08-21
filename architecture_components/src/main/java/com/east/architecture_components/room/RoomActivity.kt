package com.east.architecture_components.room

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.east.architecture_components.R
import com.east.architecture_components.room.db.AppDatabase
import com.east.architecture_components.room.db.dao.BookDao
import com.east.architecture_components.room.db.dao.PlaylistSongJoinDao
import com.east.architecture_components.room.db.dao.UserBookDao
import com.east.architecture_components.room.db.dao.UserDao
import com.east.architecture_components.room.db.entity.RoomAutoKeyUser
import com.east.architecture_components.room.db.manytomany.Playlist
import com.east.architecture_components.room.db.manytomany.PlaylistSongJoin
import com.east.architecture_components.room.db.manytomany.Song
import com.east.architecture_components.room.db.onetomany.Book
import kotlinx.android.synthetic.main.activity_room.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.random.Random

class RoomActivity : AppCompatActivity() {

    val TAG = RoomActivity::class.java.simpleName


    lateinit var userDao: UserDao
    lateinit var bookDao: BookDao
    lateinit var userBookDao: UserBookDao
    lateinit var playlistSongJoinDao: PlaylistSongJoinDao
    var excutors: ExecutorService = Executors.newCachedThreadPool()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        val appDatabase = AppDatabase.getInstance(application)
        userDao = appDatabase.userDao()
        bookDao = appDatabase.bookDao()
        userBookDao = appDatabase.userAndBookDao()
        playlistSongJoinDao = appDatabase.playlistJoinDao()


    }


    fun onClick(v: View) {
        when (v) {
            btn_query_all            -> {
                val allUser = userDao.getAllUser()
                allUser.observe(this, Observer { it ->
                    var sb = StringBuilder()
                    it.forEach {
                        var s = if (it.date != null) SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(it!!.date!!.time) else ""
                        Log.d(TAG, "btn_query_all----$it}")
                        sb.append("$it---$s\n")
                    }
                    tv_alluser.text = sb.toString()
                })
            }
            btn_query                -> {
                val liveData = userDao.findByName("%张%", "%名%")
                //一开始是空的时候是正常的,因为查询是异步查询,需要调用LiveData的Observer才行

                liveData.observe(this, Observer {
                    if (liveData!!.value == null) {
                        tv_user.text = ""
                        return@Observer
                    }
                    Log.d(TAG, "btn_query----" + liveData.value!!.toString())
                    tv_user.text = liveData.value!!.toString()

                })
            }

            btn_query_part           -> {
                userDao.findUserPart().observe(this, Observer {
                    var sb = StringBuilder()
                    it.forEach {
                        sb.append("$it\n")
                    }
                    tv_user_part.text = sb.toString()
                })
            }

            btn_add                  -> excutors.submit {
                val roomAutoKeyUser = RoomAutoKeyUser(
                    "张枫${Random.nextInt(100)}",
                    "大红的名${Random.nextInt(100)}"
                )
                roomAutoKeyUser.date = Date(System.currentTimeMillis())
                userDao.insert(roomAutoKeyUser)
            }
            btn_delete               -> {
                var task = DaoAsyncTask()
                task.execute()
            }

            btn_update               -> {
                excutors.submit {
                    val roomAutoKeyUser = userDao.getFirstUser() ?: return@submit
                    roomAutoKeyUser.firstName = "哈哈${Random.nextInt(100)}"
                    roomAutoKeyUser.lastName = "嘻嘻${Random.nextInt(100)}"
                    userDao.update(roomAutoKeyUser)
                }
            }

            btn_delete_all           -> {
                excutors.submit {
                    userDao.deleteAllUsers()
                }
            }

            //book表操作
            btn_query_all_book       -> {
                val allBooks = bookDao.queryAllBooks()
                allBooks.observe(this, Observer { it ->
                    var sb = StringBuilder()
                    it.forEach {
                        Log.d(TAG, "btn_query_all_book----$it")
                        sb.append("$it\n")
                    }
                    tv_allbooks.text = sb.toString()
                })
            }

            btn_add_book             -> {
                //user_id对应user表中的数据一定得存在
                var book =
                    Book(1, "Android Romm ${Random.nextInt(100)}")
                var book2 =
                    Book(1, "Android Romm ${Random.nextInt(100)}")
                var book3 =
                    Book(1, "Android Romm ${Random.nextInt(100)}")
                var book4 =
                    Book(1, "Android Romm ${Random.nextInt(100)}")
                bookDao.insert(book, book2, book3, book4)
            }

            //删除book表中第一行数据
            btn_delete_book          -> {
                bookDao.deleteFirstColumn()
            }

            //关联查询查找所有的Book和Users
            btn_query_all_book_users -> {
                val loadUserAndBooks = userBookDao.loadUserAndBooks()
                loadUserAndBooks.observe(this, Observer {
                    var sb = StringBuilder()
                    it.forEach {
                        sb.append("user:${it.user.toString()}\n")
                        it.books?.forEach {
                            sb.append("books:$it\n")
                        }
                    }

                    tv_allbooks_users.text = sb.toString()
                })
            }

            //多对多数据表插入数据
            btn_insert_data_manytomany_palylist -> {

                var playList1 = Playlist(1, "playlist1", "我喜欢1")
                var playList2 = Playlist(2, "playlist2", "我喜欢2")
                var playList3 = Playlist(3, "playlist3", "我喜欢3")
                playlistSongJoinDao.insertPlayList(playList1,playList2,playList3)
            }

            btn_insert_data_manytomany_song -> {

                var song1 = Song(1, "问1", "梁静茹1")
                var song2 = Song(2, "问2", "梁静茹2")
                var song3 = Song(3, "问3", "梁静茹3")

                playlistSongJoinDao.insertSong(song1,song2,song3)
            }

            btn_insert_data_manytomany_palylistsong -> {

                var playlistsong1 = PlaylistSongJoin(1, 1)
                var playlistsong2 = PlaylistSongJoin(1, 2)
                var playlistsong3 = PlaylistSongJoin(2, 2)
                var playlistsong4 = PlaylistSongJoin(3, 2)
                var playlistsong5 = PlaylistSongJoin(3, 3)
                var playlistsong6 = PlaylistSongJoin(3, 1)

                playlistSongJoinDao.insert(playlistsong1,playlistsong2,playlistsong3,playlistsong4,playlistsong5,playlistsong6)
            }

            btn_query_manytomany_playlist ->{
                playlistSongJoinDao.queryPlayList().observe(this, Observer {
                    var sb = StringBuilder()
                    it.forEach {
                        sb.append("$it\n")
                    }
                    tv_manytomany_playlist.text =  sb.toString()
                })
            }

            btn_query_manytomany_song ->{
                playlistSongJoinDao.querySong().observe(this, Observer {
                    var sb = StringBuilder()
                    it.forEach {
                        sb.append("$it\n")
                    }
                    tv_manytomany_song.text = sb.toString()
                })
            }

            btn_query_manytomany_playlistbysongid -> {
                playlistSongJoinDao.getPlaylistsForSong(1).observe(this, Observer {
                    var sb = StringBuilder()
                    it.forEach {
                        sb.append("$it\n")
                    }
                    tv_manytomany_palylistbysong.text = sb.toString()
                })
            }

            btn_query_manytomany_songbyplaylistid -> {
                playlistSongJoinDao.getSongsForPlaylist(3).observe(this, Observer {
                    var sb = StringBuilder()
                    it.forEach {
                        sb.append("$it\n")
                    }
                    tv_manytomany_songbyplaylist.text = sb.toString()
                })
            }
        }
    }


    inner class DaoAsyncTask : AsyncTask<Void, Void, RoomAutoKeyUser>() {


        override fun doInBackground(vararg params: Void?): RoomAutoKeyUser {
            return userDao!!.getFirstUser()
        }

        override fun onPostExecute(result: RoomAutoKeyUser?) {
            excutors.submit {
                userDao!!.delete(result)
            }
        }
    }
}
