package com.east.architecture_components.room.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.east.architecture_components.room.db.entity.UserAndBook

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:    获取关联的Entity
                     Entity之间可能也有一对多之间的关系。比如一个User有多个Book，通过一次查询获取多个关联的Book。
 *  @author: East
 *  @date: 2019-08-06
 * |---------------------------------------------------------------------------------------------------------------|
 */
@Dao
interface UserBookDao {
    @Query("SELECT * from user")
    @Transaction
    fun loadUserAndBooks(): LiveData<List<UserAndBook>>


}