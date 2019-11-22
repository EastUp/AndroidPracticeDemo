package com.east.customview.viewgroup_touch

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
open class TouchView @JvmOverloads constructor(
    context: Context,
    attrs : AttributeSet ?= null,
    defStyleAttr : Int = 0
    ):View(context,attrs,defStyleAttr) {

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        Log.e("TAG","View -> dispatchTouchEvent -> ${event.action}")
        return super.dispatchTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.e("TAG","View -> onTouchEvent")
        return super.onTouchEvent(event)
    }

}