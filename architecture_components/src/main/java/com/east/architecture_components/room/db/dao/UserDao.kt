package com.east.architecture_components.room.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.east.architecture_components.room.db.entity.NameTuple
import com.east.architecture_components.room.db.entity.RoomAutoKeyUser

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:  user表的增删改查
 *  @author: East
 *  @date: 2019-08-03
 * |---------------------------------------------------------------------------------------------------------------|
 */
@Dao
interface UserDao {

    @Query("select * from user")
    fun getAllUser():LiveData<List<RoomAutoKeyUser>>

    @Query("select * from user where uid in (:ids)")
    fun loadAllByIds(ids : IntArray):LiveData<List<RoomAutoKeyUser>>


    //room返回livedata或者rxjava相关类型时，会使用异步查询，所以一开始会返回null值。
    @Query("select * from user where first_name like :firstName and last_name like :lastName limit 1")
    fun findByName(firstName:String,lastName:String) :LiveData<RoomAutoKeyUser>

    //列的子集查询
    @Query("select first_name,last_name from user")
    fun findUserPart() :LiveData<List<NameTuple>>

    @Insert
    fun insertAll(vararg users: RoomAutoKeyUser)

    @Insert
    fun insert(user : RoomAutoKeyUser)

    @Delete
    fun delete(user: RoomAutoKeyUser?)

    //获取第一条数据
    @Query("select * from user order by uid asc limit 1")
    fun getFirstUser(): RoomAutoKeyUser


    @Update
    fun update(user : RoomAutoKeyUser)

    /**
     * Delete all users.
     */
    @Query("DELETE FROM user")
    fun deleteAllUsers()
}