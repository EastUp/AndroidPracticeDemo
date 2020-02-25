package com.east.architect_zenghui.architect_18_designmode11_iteration.simple3_iteration_bottomnavigation

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.east.architect_zenghui.R
import com.east.architect_zenghui.architect_18_designmode11_iteration.simple3_iteration_bottomnavigation.bottomtab.BottomTabItem

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:
 *  @author: East
 *  @date: 2020-02-25
 * |---------------------------------------------------------------------------------------------------------------|
 */
class MainBottomTabItem(builder: Builder) : BottomTabItem(builder) {


    lateinit var tabIcon : ImageView
    lateinit var tabText : TextView

    override fun getLayoutId() = R.layout.tab_main_bottom_item

    override fun initItemData() {
        tabIcon = findViewById<ImageView>(R.id.tab_icon)
        tabText = findViewById<TextView>(R.id.tab_text)
        tabIcon.setImageResource(builder.resourceIconId)
        tabText.text = builder.text
    }

    override fun setSelected(selected: Boolean) {
        tabIcon.isSelected = selected
        tabText.isSelected = selected
    }


    class Builder(context: Context) : BottomTabItem.Builder(context){
        override fun create(): BottomTabItem = MainBottomTabItem(this)
    }

}