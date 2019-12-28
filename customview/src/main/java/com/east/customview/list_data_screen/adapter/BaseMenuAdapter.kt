package com.east.customview.list_data_screen.adapter

import android.view.View
import android.view.ViewGroup

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:adapter设计模式
 *  @author: East
 *  @date: 2019-12-28
 * |---------------------------------------------------------------------------------------------------------------|
 */
abstract class BaseMenuAdapter{

    //获取总数量
    abstract fun getItemCount():Int

    //获取TabView
    abstract fun getTabView(position:Int,parent:ViewGroup):View

    //获取内容View
    abstract fun getContentView(position:Int,parent:ViewGroup):View
    //打开菜单时,条目的变化
    abstract fun openMenu(view:View)
    //关闭菜单时条目的变化
    abstract fun closeMenu(view:View)
}