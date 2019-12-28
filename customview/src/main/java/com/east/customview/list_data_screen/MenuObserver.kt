package com.east.customview.list_data_screen

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 观察者设计模式去监听是否选了ContentMenu后关闭整体的菜单
 *  @author: East
 *  @date: 2019-12-28
 * |---------------------------------------------------------------------------------------------------------------|
 */
interface MenuObserver {
    //关闭ContentMenu
    fun closeMenuContent()
}