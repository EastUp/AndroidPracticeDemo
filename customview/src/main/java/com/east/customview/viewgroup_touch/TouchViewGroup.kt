package com.east.customview.viewgroup_touch

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.RelativeLayout

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:
 *  @author: East
 *  @date: 2019-11-21
 * |---------------------------------------------------------------------------------------------------------------|
 */
class TouchViewGroup@JvmOverloads constructor(
    context: Context,
    attrs : AttributeSet?= null,
    defStyleAttr : Int = 0
): RelativeLayout(context,attrs,defStyleAttr)  {

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        Log.e("TAG","ViewGroup -> dispatchTouchEvent -> ${event.action}")
        return super.dispatchTouchEvent(event)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        Log.e("TAG","ViewGroup -> onInterceptTouchEvent")
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.e("TAG","ViewGroup -> onTouchEvent")
        return super.onTouchEvent(event)
    }
}