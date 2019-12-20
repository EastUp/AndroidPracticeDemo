package com.east.customview.custom_tablayout

import android.view.View
import android.view.ViewGroup

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: TabLayout通过Adapter的方式去加载数据
 *  @author: East
 *  @date: 2019-11-21
 * |---------------------------------------------------------------------------------------------------------------|
 */
abstract class BaseAdapter {

    /**
     *  获取item数量
     */
    abstract fun getCount():Int

    /**
     *  获取指定Position中的item
     */
    abstract fun getView(position:Int,parent : ViewGroup):View
}