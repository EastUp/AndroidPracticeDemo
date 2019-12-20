package com.east.customview.view_touch

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:
 *  @author: East
 *  @date: 2019-11-21
 * |---------------------------------------------------------------------------------------------------------------|
 */
class TouchView @JvmOverloads constructor(
    context: Context,
    attrs : AttributeSet ?= null,
    defStyleAttr : Int = 0
    ):View(context,attrs,defStyleAttr) {

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        Log.e("TAG","View -> dispatchTouchEvent")
        return super.dispatchTouchEvent(event)
    }

    /**
     *  如果设置了clickListener的监听,则super.onTouchEvent(event)会返回true
     *  因为view消费了触摸事件.
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.e("TAG","View -> onTouchEvent")
        return super.onTouchEvent(event)
    }

}