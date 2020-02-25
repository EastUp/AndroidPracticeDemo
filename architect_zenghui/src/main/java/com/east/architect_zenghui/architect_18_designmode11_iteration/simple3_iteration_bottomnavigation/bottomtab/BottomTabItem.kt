package com.east.architect_zenghui.architect_18_designmode11_iteration.simple3_iteration_bottomnavigation.bottomtab

import android.content.Context
import android.view.LayoutInflater
import android.view.View

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 底部的Item项
 *  @author: East
 *  @date: 2020-02-25
 * |---------------------------------------------------------------------------------------------------------------|
 */
abstract class BottomTabItem(var builder: Builder) {

    // TabItem的View
    var mTabItemView: View? = null

    init {
        val layoutId = getLayoutId()
        mTabItemView = LayoutInflater.from(builder.context).inflate(layoutId, null) //这样创建出来的View是没有设置 LayoutParams的
        initItemData()
    }

    fun <T : View> findViewById(id: Int): T {
        return mTabItemView!!.findViewById(id)
    }

    /**
     * 获取布局Id
     */
    abstract fun getLayoutId(): Int

    /**
     *  更新TabItemView中的数据
     */
    abstract fun initItemData()

    abstract fun setSelected(selected: Boolean)


    abstract class Builder(var context: Context) {
        var text: String? = null
        var resourceIconId: Int = 0

        fun text(text: String): Builder {
            this.text = text
            return this
        }

        fun resIcon(resourceIconId: Int): Builder {
            this.resourceIconId = resourceIconId
            return this
        }

        abstract fun create(): BottomTabItem
    }

}