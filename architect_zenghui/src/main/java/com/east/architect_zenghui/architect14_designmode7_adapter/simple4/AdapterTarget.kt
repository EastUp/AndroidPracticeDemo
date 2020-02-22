package com.east.architect_zenghui.architect14_designmode7_adapter.simple4

import android.view.View
import android.view.ViewGroup

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 这个类相当于 UsdTarget 目标接口
 *  @author: East
 *  @date: 2020-02-22
 * |---------------------------------------------------------------------------------------------------------------|
 */
interface AdapterTarget {

    /**
     * 获取多少条
     */
    fun getCount():Int

    /**
     * 获取View
     */
    fun getView(position:Int,parent:ViewGroup):View
}