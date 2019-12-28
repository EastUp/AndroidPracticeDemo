package com.east.customview.list_data_screen.adapter

import android.view.View
import android.view.ViewGroup
import com.east.customview.list_data_screen.MenuObserver

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:adapter设计模式
 *  @author: East
 *  @date: 2019-12-28
 * |---------------------------------------------------------------------------------------------------------------|
 */
abstract class BaseMenuAdapter{
    var mObservers = ArrayList<MenuObserver>()

    //获取总数量
    abstract fun getItemCount():Int

    //获取TabView
    abstract fun getTabView(position:Int,parent:ViewGroup):View

    //获取内容View
    abstract fun getContentView(position:Int,parent:ViewGroup):View
    //打开菜单时,条目的变化
    open fun openMenu(view:View){}
    //关闭菜单时条目的变化
    open fun closeMenu(view:View){}

    /**
     *  下面两个是观察者设计模式的内容
     */

    /**
     * 绑定观察者监听
     */
    fun registerDataSetObserver(menuObserver: MenuObserver){
        mObservers.add(menuObserver)
    }

    /**
     * 解除观察者的监听
     */
    fun unRegisterDataSetObserver(menuObserver: MenuObserver){
        mObservers.remove(menuObserver)
    }
}