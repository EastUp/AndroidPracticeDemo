package com.east.architecture_components.paginglibrary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.paging.Config
import androidx.paging.toLiveData
import com.east.architecture_components.paginglibrary.CheeseDb
import com.east.architecture_components.paginglibrary.entity.Cheese
import com.east.architecture_components.paginglibrary.ioThread

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:
 *  @author: East
 *  @date: 2019-08-13
 * |---------------------------------------------------------------------------------------------------------------|
 */
class CheeseViewModel(app:Application) : AndroidViewModel(app) {

    val dao  = CheeseDb.getInstance(app).cheeseDao()

    /**
     *  我们在这里使用-ktx Kotlin扩展函数，否则你将使用LivePagedListBuilder()和PagedList.Config.Builder()
     */
    val allCheese = dao.allCheesesByName().toLiveData(Config(
        pageSize = 30,
        enablePlaceholders = true,
        maxSize = 200
    ))

    fun insert(text : CharSequence) = ioThread {
        dao.insert(Cheese(id = 0,name = text.toString()))
    }

    fun remove(cheese:Cheese) = ioThread {
        dao.delete(cheese)
    }



}