package com.east.recyclerview.swap

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 用于上下拖动时交换位置。
 *  @author: East
 *  @date: 2019-11-05
 * |---------------------------------------------------------------------------------------------------------------|
 */
interface OnItemCallbackListener {

    /**
     * @param fromPosition 起始位置
     * @param toPosition 移动位置
     */
    fun move(fromPosition:Int,toPosition:Int)
}