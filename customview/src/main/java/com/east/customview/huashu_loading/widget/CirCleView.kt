package com.east.customview.huashu_loading.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:加载动画中的小圆
 *  @author: East
 *  @date: 2019-12-30
 * |---------------------------------------------------------------------------------------------------------------|
 */
class CirCleView @JvmOverloads constructor(
    context: Context,
    attrs:AttributeSet ?= null,
    defStyleAttr:Int = 0
):View(context, attrs, defStyleAttr) {
    var mColor = Color.RED
    private var mPaint = Paint()
    init {
        mPaint.isAntiAlias = true
        mPaint.isDither = true
        mPaint.color = mColor
    }

    override fun onDraw(canvas: Canvas) {
        var cx = width/2
        var cy = height/2
        canvas.drawCircle(cx.toFloat(), cy.toFloat(), cx.toFloat(),mPaint)
    }

    /**
     *  改变小圆球的颜色
     */
    fun changeColor(color:Int){
        mColor = color
        mPaint.color = color
        invalidate()
    }
}