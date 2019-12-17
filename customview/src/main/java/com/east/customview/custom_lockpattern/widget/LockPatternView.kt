package com.east.customview.custom_lockpattern.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:  九宫格自定义控件
 *  @author: East
 *  @date: 2019-12-14
 * |---------------------------------------------------------------------------------------------------------------|
 */versions.gradle
class LockPatternView @JvmOverloads constructor(
    context: Context,
    attrs:AttributeSet,
    defStyleAttr:Int) : View(context,attrs,defStyleAttr) {

    private var mIsInit = false //是否已经初始化过点和画笔

    private var mPoints = Array(3){Array<Point?>(3){null}} //所有的点


    override fun onDraw(canvas: Canvas?) {
    }


    class Point(var centerX:Int,var centerY:Int,var index:Int){
        companion object{
            val STATUS_NORMAL = 1
            val STATUS_PRESSED = 2
            val STATUS_ERROR = 3
        }

        var status = STATUS_NORMAL
    }

}