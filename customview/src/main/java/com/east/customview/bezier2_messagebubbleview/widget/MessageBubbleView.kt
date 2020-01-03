package com.east.customview.bezier2_messagebubbleview.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.animation.OvershootInterpolator
import com.east.customview.bezier2_messagebubbleview.BubbleTouchListener
import com.east.customview.bezier2_messagebubbleview.BubbleUtils
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 贝塞尔曲线-可拖动爆炸的消息气泡View
 *  @author: East
 *  @date: 2019-12-31
 * |---------------------------------------------------------------------------------------------------------------|
 */
class MessageBubbleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var mPaint = Paint()
    //固定的圆
    private lateinit var mFixationPoint: PointF
    //可拖动的圆
    private lateinit var mDragPoint: PointF
    //可拖圆的半径大小
    private var mDragRadius = dip2px(10f)
    //固定圆的最大半径(初始半径)
    private var mFixationRadiusMax = dip2px(7f)
    //最小半径的值
    private var mFixationRadiusMin = dip2px(3f)
    private var mFixationRadius: Float = 0f //固定圆的半径
    //需要绘制的拖动View的Bitmap
    private var mDragBitmap: Bitmap? = null
    //松开手指后消息气泡需要进行的操作监听
    var mMessageBubbleListener: MessageBubbleListener? = null

    companion object {
        fun attachView(view: View, disapearListener: BubbleDisapearListener) {
            view.setOnTouchListener(BubbleTouchListener(view, view.context, disapearListener))
        }
    }

    init {
        mPaint.color = Color.RED
        mPaint.isAntiAlias = true//抗锯齿
        mPaint.isDither = true //防抖动
    }

    override fun onDraw(canvas: Canvas) {
        if (!this::mDragPoint.isInitialized || !this::mFixationPoint.isInitialized) {
            return
        }
        //3.画可拖拽的圆
        canvas.drawCircle(mDragPoint.x, mDragPoint.y, mDragRadius, mPaint)

        var bezeierPath = getBezeierPath()

        if (bezeierPath != null) {
            canvas.drawCircle(mFixationPoint.x, mFixationPoint.y, mFixationRadius, mPaint)
            canvas.drawPath(bezeierPath, mPaint)
        }
        if (mDragBitmap != null) {
            // 搞一个渐变动画(解决抖动)
            canvas.drawBitmap(
                mDragBitmap!!,
                mDragPoint.x - mDragBitmap!!.width / 2,
                mDragPoint.y - mDragBitmap!!.height / 2,
                null
            )
        }
    }

    /**
     *  获取贝塞尔曲线Path
     */
    private fun getBezeierPath(): Path? {
        //4.计算固定圆半径
        val distance = getDistance(mFixationPoint, mDragPoint)
        mFixationRadius = mFixationRadiusMax - distance / 14
        // 超过一定距离 贝塞尔和固定圆都不要画了
        if (mFixationRadius < mFixationRadiusMin)
            return null

        //求角a
        var dy = mDragPoint.y - mFixationPoint.y
        var dx = mDragPoint.x - mFixationPoint.x
        var digreeA = atan(dy / dx)
        Log.d("TAG", "digreeA:$digreeA--sin:${sin(digreeA)}--cos:${cos(digreeA)}")

        //p0
        var p0x = mFixationPoint.x + mFixationRadius * sin(digreeA)
        var p0y = mFixationPoint.y - mFixationRadius * cos(digreeA)

        //p1
        var p1x = mDragPoint.x + mDragRadius * sin(digreeA)
        var p1y = mDragPoint.y - mDragRadius * cos(digreeA)

        //p2
        var p2x = mDragPoint.x - mDragRadius * sin(digreeA)
        var p2y = mDragPoint.y + mDragRadius * cos(digreeA)

        //p3
        var p3x = mFixationPoint.x - mFixationRadius * sin(digreeA)
        var p3y = mFixationPoint.y + mFixationRadius * cos(digreeA)

        var controlPointX = mFixationPoint.x + (mDragPoint.x - mFixationPoint.x) / 2
        var controlPointY = mFixationPoint.y + (mDragPoint.y - mFixationPoint.y) / 2

        var path = Path()
        path.moveTo(p0x, p0y)
        //贝塞尔曲线1阶(前两个参数是控制点,后两个参数是终点)
        path.quadTo(controlPointX, controlPointY, p1x, p1y)

        // 画第二条
        path.lineTo(p2x, p2y)
        path.quadTo(controlPointX, controlPointY, p3x, p3y)
        path.close()
        return path
    }

    /**
     *  获取两个点的距离
     */
    private fun getDistance(pointF: PointF, pointF1: PointF): Float {
        var dx = pointF.x - pointF1.x
        var dy = pointF.y - pointF1.y
        return sqrt(dy * dy + dx * dx)
    }

//    override fun onTouchEvent(event: MotionEvent): Boolean {
//        when(event.action){
//            MotionEvent.ACTION_DOWN -> {
//                //1.初始化两个圆的圆心
//                var downx = event.x
//                var downy = event.y
//                initPoint(downx,downy)
//            }
//            MotionEvent.ACTION_MOVE -> {
//                //2.更新可拖拽圆的圆心
//                var movex = event.x
//                var movey = event.y
//                updateDragPoint(movex,movey)
//            }
//            MotionEvent.ACTION_UP -> {
//
//            }
//        }
//        invalidate()
//        return true
//    }

    /**
     * 初始化两个圆心
     */
    fun initPoint(downx: Float, downy: Float) {
        mDragPoint = PointF(downx, downy)
        mFixationPoint = PointF(downx, downy)
        invalidate()
    }

    /**
     *  更新可拖拽圆的圆心
     */
    fun updateDragPoint(movex: Float, movey: Float) {
        mDragPoint = PointF(movex, movey)
        invalidate()
    }

    private fun dip2px(dip: Float) =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, resources.displayMetrics)

    fun setDragBitmap(bitmap: Bitmap) {
        mDragBitmap = bitmap
    }

    /**
     *  处理松开手时效果(回弹还是爆炸)
     */
    fun handleActionUp() {
        if (mFixationRadius > mFixationRadiusMin) {//回弹
            //ValueAnimator 值变化的动画  1 变化到 0
            var animator: ValueAnimator = ObjectAnimator.ofFloat(1f, 0f)
            val start = PointF(mFixationPoint.x, mFixationPoint.y)
            //因为updatePoint会改变mDragPoint的值所以需要先保存一下
            val end = PointF(mDragPoint.x, mDragPoint.y)
            animator.duration = 250
            animator.addUpdateListener {
                val percent = it.animatedValue as Float
                Log.d("TAG", "percent:$percent")
                var pointF = BubbleUtils.getPointByPercent(start, end, percent)
                updateDragPoint(pointF.x, pointF.y)
            }
            // 设置一个差值器 在结束的时候回弹
            animator.interpolator = OvershootInterpolator(4f)
            animator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    mMessageBubbleListener?.restore()
                }
            })
            animator.start()
        } else {//爆炸
            mMessageBubbleListener?.bombDismiss(mDragPoint)
        }
    }


    /**
     *  View拖动爆炸后的监听
     */
    interface BubbleDisapearListener {
        fun disappear()
    }

    interface MessageBubbleListener {
        //还原
        fun restore()

        //爆炸消失
        fun bombDismiss(pointF: PointF)
    }

}


