package com.east.customview.practice_paint.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 仿58同城数据加载
 *  @author: East
 *  @date: 2019-11-13
 * |---------------------------------------------------------------------------------------------------------------|
 */
class ShapeView  @JvmOverloads constructor(
    context: Context,
    attrs : AttributeSet?=null,
    defStyleAttr : Int = 0
): View(context,attrs,defStyleAttr){

    private var mPaint : Paint
    private var mCurrentShape = Shape.Circle

    init {
        mPaint = Paint()
        mPaint.isAntiAlias = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = MeasureSpec.getSize(widthMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(Math.min(width,height),Math.min(width,height))
    }

    override fun onDraw(canvas: Canvas) {
        val center = width / 2
        when(mCurrentShape){
            Shape.Circle -> {
                mPaint.color = Color.YELLOW
                canvas.drawCircle(center.toFloat(), center.toFloat(), center.toFloat(),mPaint)
            }

            Shape.Square ->{
                mPaint.color = Color.BLUE
                var rect = RectF(0f,0f, width.toFloat(), height.toFloat())
                canvas.drawRoundRect(rect,10f,10f,mPaint)
            }

            Shape.Tringle -> { //画等边三角形,这样后面做动画旋转会好看
                mPaint.color = Color.RED
                var path = Path()
                val triangleY = (width * sin(Math.toRadians(60.0))).toFloat()
                path.moveTo((width/2).toFloat(), 0F)
                path.lineTo(0f, triangleY)
                path.lineTo(width.toFloat(), triangleY)
                path.close() //会自动连接到起始点
                canvas.drawPath(path,mPaint)
            }
        }
    }

    @Synchronized
    fun exchange(){
        when(mCurrentShape){
            Shape.Circle -> {
               mCurrentShape = Shape.Square
            }

            Shape.Square ->{
                mCurrentShape = Shape.Tringle
            }

            Shape.Tringle -> {
                mCurrentShape = Shape.Circle
            }
        }
        postInvalidate()
    }


    enum class Shape{
        Circle,Square,Tringle
    }
}