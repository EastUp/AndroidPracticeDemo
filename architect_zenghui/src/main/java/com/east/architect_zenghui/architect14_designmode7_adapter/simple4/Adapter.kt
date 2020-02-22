package com.east.architect_zenghui.architect14_designmode7_adapter.simple4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.east.architect_zenghui.R

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 适配器
 *  @author: East
 *  @date: 2020-02-22
 * |---------------------------------------------------------------------------------------------------------------|
 */
class Adapter(var mItem:MutableList<String>):AdapterTarget {


    override fun getCount() = mItem.size

    override fun getView(position: Int, parent: ViewGroup): View {
        val textView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_eastriselistview,
            parent,
            false
        ) as TextView

        textView.text = mItem[position]
        return textView
    }
}