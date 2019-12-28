package com.east.customview.list_data_screen.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.east.customview.R

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 多条目筛选的Adapter
 *  @author: East
 *  @date: 2019-12-28
 * |---------------------------------------------------------------------------------------------------------------|
 */
class ListDataScreenAdapter: BaseMenuAdapter() {

    private var datas = arrayOf("类型","品牌","价格","更多")
    override fun getItemCount() = datas.size

    override fun getTabView(position: Int, parent: ViewGroup): View {
        val tabView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_tabview, parent, false) as TextView
        tabView.setTextColor(Color.WHITE)
        tabView.text = datas[position]
        return tabView
    }

    override fun getContentView(position: Int, parent: ViewGroup): View {
        val contentView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_content, parent, false) as TextView
        contentView.text = datas[position]
        return contentView
    }

    override fun openMenu(view:View) {
        val textView = view as TextView
        textView.setTextColor(Color.RED)
    }

    override fun closeMenu(view:View) {
        val textView = view as TextView
        textView.setTextColor(Color.WHITE)
    }

}