package com.east.architecture_components.room.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.east.architecture_components.room.db.onetomany.Book

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:  book表的增删
 *  @author: East
 *  @date: 2019-08-08
 * |---------------------------------------------------------------------------------------------------------------|
 */
@Dao
interface BookDao {
//    onConflict：默认值是OnConflictStrategy.ABORT，表示当插入有冲突的时候的处理策略。OnConflictStrategy封装了Room解决冲突的相关策略：
//
//           1. OnConflictStrategy.REPLACE：冲突策略是取代旧数据同时继续事务。
//           2. OnConflictStrategy.ROLLBACK：冲突策略是回滚事务。
//           3. OnConflictStrategy.ABORT：冲突策略是终止事务。
//           4. OnConflictStrategy.FAIL：冲突策略是事务失败。
//           5. OnConflictStrategy.IGNORE：冲突策略是忽略冲突。


    @Query("select * from book")
    fun queryAllBooks():LiveData<List<Book>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg book: Book)


    //删除第一条数据
    @Query("delete from book where book_id = (select book_id from book limit 1)")
    fun deleteFirstColumn()


}