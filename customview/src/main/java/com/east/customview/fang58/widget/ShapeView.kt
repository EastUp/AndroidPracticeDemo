package com.east.customview.fang58.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.east.customview.R
import kotlin.math.sin

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 仿58同城数据加载
 *  @author: East
 *  @date: 2019-11-13
 * |---------------------------------------------------------------------------------------------------------------|
 */
class ShapeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var mPaint: Paint = Paint()
    var mCurrentShape = Shape.Circle //当前的状态
    private var mPath :Path ?= null
    init {
        mPaint.isAntiAlias = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = MeasureSpec.getSize(widthMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(width.coerceAtMost(height), width.coerceAtMost(height))
    }

    override fun onDraw(canvas: Canvas) {
        val center = width / 2
        when (mCurrentShape) {
            Shape.Circle -> {
                mPaint.color = ContextCompat.getColor(context, R.color.circle)
                canvas.drawCircle(center.toFloat(), center.toFloat(), center.toFloat(), mPaint)
            }

            Shape.Square -> {
                mPaint.color = ContextCompat.getColor(context, R.color.rect)
                var rect = RectF(0f, 0f, width.toFloat(), height.toFloat())
                canvas.drawRoundRect(rect, 5f, 5f, mPaint)
            }

            Shape.Tringle -> { //画等边三角形,这样后面做动画旋转会好看
                mPaint.color = ContextCompat.getColor(context, R.color.triangle)
                if(mPath == null){
                    mPath = Path()
                    val triangleY = (width * sin(Math.toRadians(60.0))).toFloat()
                    mPath!!.moveTo((width / 2).toFloat(), 0F)
                    mPath!!.lineTo(0f, triangleY)
                    mPath!!.lineTo(width.toFloat(), triangleY)
                    mPath!!.close() //会自动连接到起始点
                }
                canvas.drawPath(mPath!!, mPaint)
            }
        }
    }

    @Synchronized
    fun exchange() {
        mCurrentShape = when (mCurrentShape) {
            Shape.Circle -> {
                Shape.Square
            }

            Shape.Square -> {
                Shape.Tringle
            }

            Shape.Tringle -> {
                Shape.Circle
            }
        }
        postInvalidate()
    }


    enum class Shape {
        Circle, Square, Tringle
    }
}