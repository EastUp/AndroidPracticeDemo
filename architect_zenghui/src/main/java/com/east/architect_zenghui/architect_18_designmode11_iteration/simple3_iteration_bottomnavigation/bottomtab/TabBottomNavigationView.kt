package com.east.architect_zenghui.architect_18_designmode11_iteration.simple3_iteration_bottomnavigation.bottomtab

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.LinearLayoutCompat
import com.east.architect_zenghui.architect_18_designmode11_iteration.simple3_iteration_bottomnavigation.bottomtab.iterator.TabIterator

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:  底部导航View
 *  @author: East
 *  @date: 2020-02-25
 * |---------------------------------------------------------------------------------------------------------------|
 */
class TabBottomNavigationView @JvmOverloads constructor(
    context: Context,
    attrs:AttributeSet ?= null,
    defAttrStyle:Int = 0
):LinearLayoutCompat(context,attrs,defAttrStyle){
    var mTabItems = ArrayList<BottomTabItem>()

    var mCurrentItemIndex = 0

    var mOnTabItemViewClickListener: OnTabItemViewClickListener ?= null

    fun addBottomTabItems(tabIterator:TabIterator<BottomTabItem>){
        mTabItems.clear()
        var index = 0
        while (tabIterator.hasNext()){
            val bottomTabItem = tabIterator.next()
            mTabItems.add(bottomTabItem)

            val tabView = bottomTabItem.mTabItemView!!
            addView(tabView)

            //设置View的布局参数
            var layoutParams = tabView.layoutParams as LayoutParams
            layoutParams.weight = 1f
            layoutParams.gravity = Gravity.CENTER_VERTICAL
            tabView.layoutParams = layoutParams


            //设置点击监听
            setOnTabItemClick(bottomTabItem,index++)
        }

        mTabItems[0].setSelected(true)

    }

    /**
     * 设置点击监听
     */
    private fun setOnTabItemClick(bottomTabItem: BottomTabItem, index: Int) {
        bottomTabItem.mTabItemView!!.setOnClickListener {
            mTabItems[mCurrentItemIndex].setSelected(false)
            mCurrentItemIndex = index
            mTabItems[mCurrentItemIndex].setSelected(true)

            mOnTabItemViewClickListener?.onClick(index,bottomTabItem)
        }
    }

    /**
     *  设置Tab的监听回调
     */
    fun setOnTabItemViewClickListener(onTabItemViewClickListener: OnTabItemViewClickListener){
        this.mOnTabItemViewClickListener= onTabItemViewClickListener
    }

}