package com.east.architecture_components.paginglibrary.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.east.architecture_components.paginglibrary.entity.Cheese

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:
 *  @author: East
 *  @date: 2019-08-10
 * |---------------------------------------------------------------------------------------------------------------|
 */
@Dao
interface CheeseDao {
    /**
     * Room知道如何返回一个LivePagedListProvider，我们可以从中获取LiveData并通过ViewModel将其提供给UI
     */
    @Query("select * from Cheese order by name collate nocase asc")
    fun allCheesesByName(): DataSource.Factory<Int,Cheese>

    @Insert
    fun insert(cheeses: List<Cheese>)

    @Insert
    fun insert(cheese: Cheese)

    @Delete
    fun delete(cheese: Cheese)

}