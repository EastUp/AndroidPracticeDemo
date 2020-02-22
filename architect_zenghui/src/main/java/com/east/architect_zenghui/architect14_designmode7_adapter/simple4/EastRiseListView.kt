package com.east.architect_zenghui.architect14_designmode7_adapter.simple4

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:
 *  @author: East
 *  @date: 2020-02-22
 * |---------------------------------------------------------------------------------------------------------------|
 */
class EastRiseListView @JvmOverloads constructor(
    context: Context,
    attrs:AttributeSet ?= null,
    defStyleAttr : Int = 0
    ) : ScrollView(context,attrs,defStyleAttr){

    var mContainerView : LinearLayout = LinearLayout(context)
    var mAdapter :Adapter ?= null

    init {
        mContainerView.orientation = LinearLayout.VERTICAL
        addView(mContainerView,0)
    }

    override fun addView(child: View?) {
        mContainerView.addView(child)
    }

    fun setAdapter(adapter :Adapter){
        mAdapter = adapter
        // 观察者，注册反注册
        val mItem = mAdapter!!.mItem
        for (i in 0 until mItem.size){
            val view = mAdapter!!.getView(i, mContainerView)
            mContainerView.addView(view)
        }
    }
}