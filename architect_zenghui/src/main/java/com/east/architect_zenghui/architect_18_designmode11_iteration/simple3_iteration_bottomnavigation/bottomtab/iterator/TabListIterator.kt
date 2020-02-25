package com.east.architect_zenghui.architect_18_designmode11_iteration.simple3_iteration_bottomnavigation.bottomtab.iterator

import com.east.architect_zenghui.architect_18_designmode11_iteration.simple3_iteration_bottomnavigation.bottomtab.BottomTabItem

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:
 *  @author: East
 *  @date: 2020-02-25
 * |---------------------------------------------------------------------------------------------------------------|
 */
class TabListIterator:TabIterator<BottomTabItem> {

    private var mTabItems : MutableList<BottomTabItem> = ArrayList()

    var index = 0

    fun addTabItem (item:BottomTabItem){
        mTabItems.add(item)
    }

    override fun next(): BottomTabItem  = mTabItems[index++]

    override fun hasNext(): Boolean = index < mTabItems.size
}