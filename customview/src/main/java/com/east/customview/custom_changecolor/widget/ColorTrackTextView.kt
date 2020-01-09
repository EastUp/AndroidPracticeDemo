package com.east.customview.custom_changecolor.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.widget.TextView
import com.east.customview.R

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 玩转字体变色,继承自TextView,这样宽高,字帖颜色,大小就都不用我们指定了
 *  @author: East
 *  @date: 2019-11-09
 * |---------------------------------------------------------------------------------------------------------------|
 */
class ColorTrackTextView @JvmOverloads constructor(
    context:Context,
    attrs: AttributeSet ?= null,
    defStyleAttr : Int = 0
):TextView(context,attrs,defStyleAttr) {

    private var mOriginColor : ColorStateList
    private var mChangeColor : ColorStateList

    // 1. 实现一个文字两种颜色 - 绘制不变色字体的画笔
    private var mOriginPaint:Paint
    // 1. 实现一个文字两种颜色 - 绘制变色字体的画笔
    private var mChangePaint:Paint
    // 1. 实现一个文字两种颜色 - 当前的进度
    private var mCurrentProgress = 0f

    // 2.实现不同朝向
    private var mDirection = Direction.LEFT_TO_RIGHT

    enum class Direction{
        LEFT_TO_RIGHT,RIGHT_TO_LEFT
    }

    init {
        var typedArray = context.obtainStyledAttributes(attrs,R.styleable.ColorTrackTextView)

        mOriginColor = typedArray.getColorStateList(R.styleable.ColorTrackTextView_originColor) ?: ColorStateList(
            arrayOf(drawableState), intArrayOf(Color.BLACK)
        )

        mChangeColor = typedArray.getColorStateList(R.styleable.ColorTrackTextView_changeColor) ?: ColorStateList(
            arrayOf(drawableState), intArrayOf(Color.RED)
        )
        typedArray.recycle()

        mOriginPaint = getPaintByColor(mOriginColor.getColorForState(drawableState,Color.BLACK))
        mChangePaint = getPaintByColor(mChangeColor.getColorForState(drawableState,Color.RED))

    }

    /**
     * 1.根据颜色获取画笔
     */
    fun getPaintByColor(color : Int):Paint{
        var paint  = Paint()
        paint.isAntiAlias = true //抗锯齿
        paint.isDither = true  //仿抖动
        paint.color = color//设置颜色
        paint.textSize = textSize// 设置字体的大小  就是TextView的字体大小
        return paint
    }

    override fun onDraw(canvas: Canvas) {
        val middle = width * mCurrentProgress

        //从左到右
        if(mDirection == Direction.LEFT_TO_RIGHT){
            drawText(canvas,mChangePaint,0f,middle)
            drawText(canvas,mOriginPaint,middle,width.toFloat())
        }else{
            // 右边是红色左边是黑色
            drawText(canvas,mChangePaint,width-middle,width.toFloat())
            //绘制变色
            drawText(canvas,mOriginPaint,0f,width-middle)
        }
    }

    fun drawText(canvas: Canvas,paint:Paint,start:Float,end:Float){
        //先保存画布的状态,防止从左向右裁剪后,再从右向左裁剪没效果
        canvas.save()
        //从左向右裁剪
        canvas.clipRect(start,0f,end,height.toFloat()) //裁剪画布
        var bounds = Rect()
        paint.getTextBounds(text.toString(),0,text.length,bounds)
        var x = (width - bounds.width())/2
        val fontMetricsInt = paint.fontMetricsInt
        var dy = (fontMetricsInt.descent - fontMetricsInt.ascent)/2 - fontMetricsInt.descent
        var y = height/2+dy
        canvas.drawText(text.toString(),x.toFloat(),y.toFloat(),paint)
        canvas.restore()//还原canvas之前save的状态
    }

    fun setProgress(progress:Float){
        mCurrentProgress = progress
        invalidate()
    }

    /**
     *  设置方向
     */
    fun setDirection(direction:Direction){
        this.mDirection = direction
    }

    fun setChangeColor(color : Int){
        mChangePaint.color = color
    }

    fun setOriginColor(color : Int){
        mOriginPaint.color = color
    }


}