package com.east.customview.beisaier1_meesagebuddleview.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import kotlin.math.sqrt

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 贝塞尔曲线-可拖动的消息气泡View
 *  @author: East
 *  @date: 2019-12-31
 * |---------------------------------------------------------------------------------------------------------------|
 */
class MessageBubbleView @JvmOverloads constructor(
    context:Context,
    attrs:AttributeSet ?= null,
    defStyleAttr : Int = 0
):View(context, attrs, defStyleAttr){

    private var mPaint = Paint()
    //固定的圆
    private var mFixationPoint : PointF ?= null
    //可拖动的圆
    private var mDragPoint : PointF ?= null
    //可拖圆的半径大小
    private var mDragRadius = dip2px(10f)
    //固定圆的最大半径(初始半径)
    private var mFixationRadiusMax = dip2px(7f)
    //最小半径的值
    private var mFixationRadiusMin = dip2px(3f)
    private var mFixationRadius:Float = 0f //固定圆的半径

    init {
        mPaint.color = Color.RED
        mPaint.isAntiAlias = true//抗锯齿
        mPaint.isDither = true //防抖动
    }

    override fun onDraw(canvas: Canvas) {
        if (mDragPoint == null || mFixationPoint == null) {
            return
        }
        //3.画可拖拽的圆
        canvas.drawCircle(mDragPoint!!.x,mDragPoint!!.y,mDragRadius,mPaint)
        //4.计算固定圆半径
        val distance = getDistance(mFixationPoint!!, mDragPoint!!)
        mFixationRadius = mFixationRadiusMax - distance/14
        // 小到一定层度就不见了（不画了）
        if(mFixationRadius>=mFixationRadiusMin)
            canvas.drawCircle(mFixationPoint!!.x,mFixationPoint!!.y,mFixationRadius,mPaint)
    }

    /**
     *  获取两个点的距离
     */
    private fun getDistance(pointF: PointF, pointF1: PointF):Float{
        var dx = pointF.x-pointF1.x
        var dy = pointF.y-pointF1.y
        return sqrt(dy*dy+dx*dx)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                //1.初始化两个圆的圆心
                var downx = event.x
                var downy = event.y
                initPoint(downx,downy)
            }
            MotionEvent.ACTION_MOVE -> {
                //2.更新可拖拽圆的圆心
                var movex = event.x
                var movey = event.y
                updateDragPoint(movex,movey)
            }
            MotionEvent.ACTION_UP -> {

            }
        }
        invalidate()
        return true
    }

    /**
     * 初始化两个圆心
     */
    private fun initPoint(downx: Float, downy: Float) {
        mDragPoint = PointF(downx,downy)
        mFixationPoint = PointF(downx,downy)
    }

    /**
     *  更新可拖拽圆的圆心
     */
    private fun updateDragPoint(movex: Float, movey: Float) {
        mDragPoint = PointF(movex,movey)
    }





    private fun dip2px(dip: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dip,resources.displayMetrics)

}