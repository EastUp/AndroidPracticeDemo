package com.east.recyclerview.swap

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 用于上下拖动时交换位置。以及左右滑动删除
 *  @author: East
 *  @date: 2019-11-05
 * |---------------------------------------------------------------------------------------------------------------|
 */
interface OnItemCallbackListener {

    /**
     * @param fromPosition 起始位置
     * @param toPosition 移动位置
     */
    fun onMove(fromPosition:Int, toPosition:Int)


    //左滑
    fun onSwipeLeft(position:Int)

    //右滑
    fun onSwipeRight(position: Int)
}