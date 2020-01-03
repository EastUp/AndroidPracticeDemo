package com.east.customview.besier1_messagebubbleview.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.sin
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
    private lateinit var mFixationPoint : PointF
    //可拖动的圆
    private lateinit var mDragPoint : PointF
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
        if (!this::mDragPoint.isInitialized  || !this::mFixationPoint.isInitialized ) {
            return
        }
        //3.画可拖拽的圆
        canvas.drawCircle(mDragPoint.x,mDragPoint.y,mDragRadius,mPaint)

        var bezeierPath = getBezeierPath()

        if(bezeierPath !=null ){
            canvas.drawCircle(mFixationPoint.x,mFixationPoint.y,mFixationRadius,mPaint)
            canvas.drawPath(bezeierPath,mPaint)
        }
    }

    /**
     *  获取贝塞尔曲线Path
     */
    private fun getBezeierPath(): Path? {
        //4.计算固定圆半径
        val distance = getDistance(mFixationPoint, mDragPoint)
        mFixationRadius = mFixationRadiusMax - distance/14
        // 超过一定距离 贝塞尔和固定圆都不要画了
        if(mFixationRadius<mFixationRadiusMin)
            return null

        //求角a
        var dy = mDragPoint.y - mFixationPoint.y
        var dx = mDragPoint.x - mFixationPoint.x
        var digreeA = atan(dy/dx)
        Log.d("TAG","digreeA:$digreeA--sin:${sin(digreeA)}--cos:${cos(digreeA)}")

        //p0
        var p0x = mFixationPoint.x+mFixationRadius* sin(digreeA)
        var p0y = mFixationPoint.y-mFixationRadius* cos(digreeA)

        //p1
        var p1x = mDragPoint.x+mDragRadius* sin(digreeA)
        var p1y = mDragPoint.y - mDragRadius* cos(digreeA)

        //p2
        var p2x = mDragPoint.x - mDragRadius* sin(digreeA)
        var p2y = mDragPoint.y + mDragRadius* cos(digreeA)

        //p3
        var p3x = mFixationPoint.x - mFixationRadius* sin(digreeA)
        var p3y = mFixationPoint.y + mFixationRadius* cos(digreeA)

        var controlPointX = mFixationPoint.x + (mDragPoint.x - mFixationPoint.x)/2
        var controlPointY = mFixationPoint.y + (mDragPoint.y - mFixationPoint.y)/2

        var path = Path()
        path.moveTo(p0x,p0y)
        //贝塞尔曲线1阶(前两个参数是控制点,后两个参数是终点)
        path.quadTo(controlPointX,controlPointY,p1x,p1y)

        // 画第二条
        path.lineTo(p2x,p2y)
        path.quadTo(controlPointX,controlPointY,p3x,p3y)
        path.close()
        return path
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