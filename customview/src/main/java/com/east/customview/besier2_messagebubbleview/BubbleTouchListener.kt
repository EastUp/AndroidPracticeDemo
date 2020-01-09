package com.east.customview.besier2_messagebubbleview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.PointF
import android.graphics.drawable.AnimationDrawable
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import com.east.customview.R
import com.east.customview.besier2_messagebubbleview.widget.MessageBubbleView

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 可拖动爆炸view的触摸监听(先隐藏原来的view,新加view到windowsManager这样才能拖到ActionBar和状态栏)
 *  @author: East
 *  @date: 2020-01-02
 * |---------------------------------------------------------------------------------------------------------------|
 */
class BubbleTouchListener
    (var mDragView:View,// 原来需要拖动爆炸的View
     var mContext: Context,
     var mDisappearListener : MessageBubbleView.BubbleDisapearListener?= null):View.OnTouchListener,MessageBubbleView.MessageBubbleListener {

    private var mMessageBubbleView : MessageBubbleView = MessageBubbleView(mContext)
    private var mWindowManager:WindowManager = mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private var mParams : WindowManager.LayoutParams = WindowManager.LayoutParams()
    // 爆炸动画
    private val mBombFrame: FrameLayout = FrameLayout(mContext)
    private val mBombImage: ImageView = ImageView(mContext)

    init {
        //松开手之后的是还原还是爆炸的监听
        mMessageBubbleView.mMessageBubbleListener = this
        //背景透明
        mParams.format = PixelFormat.TRANSPARENT
        mBombImage.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        mBombFrame.addView(mBombImage)

    }


    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                //1.先把之前的View置为不可见
                mDragView.visibility = View.INVISIBLE
                //2.将新建的View添加到WindowManager中
                mWindowManager.addView(mMessageBubbleView, mParams)
                //3.给消息气泡设置一个bitmap
                //获取拖动View的Bitmap
                val bitmap = getBitmapByView(v)
                mMessageBubbleView.setDragBitmap(bitmap)
                //4.初始化MessageBubbleView的点(需要是这个View的中心位置)
                var location = IntArray(2)
                v.getLocationOnScreen(location)
                mMessageBubbleView.initPoint(location[0].toFloat()+v.width/2,location[1].toFloat()+v.height/2 - BubbleUtils.getStatusBarHeight(mContext))
            }
            MotionEvent.ACTION_MOVE -> {
                //5.更新位置
                mMessageBubbleView.updateDragPoint(event.rawX,event.rawY - BubbleUtils.getStatusBarHeight(mContext))
            }
            MotionEvent.ACTION_UP -> {
                mMessageBubbleView.handleActionUp()
            }
        }
        return true
    }

    /**
     * 从一个View中获取Bitmap(前提是在获取Bitmap之前，View显示在屏幕上了已经)
     */
    private fun getBitmapByView(view: View) :Bitmap{
        val width: Int = view.measuredWidth
        val height: Int = view.measuredHeight
        val bp: Bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bp)
        view.draw(canvas)
        canvas.save()
        return bp
    }

    /**
     * 还原View
     */
    override fun restore() {
        //移除原来的View
        mWindowManager.removeView(mMessageBubbleView)
        //把之前隐藏的View显示出来
        mDragView.visibility = View.VISIBLE
    }

    /**
     * 爆炸消失
     */
    override fun bombDismiss(pointF: PointF) {
        // 要去执行爆炸动画 （帧动画）
        // 原来的View的View肯定要移除
        mWindowManager.removeView(mMessageBubbleView)
        //在WindowManager上加一个爆炸动画
        mWindowManager.addView(mBombFrame,mParams)
        //设置ImageView的背景是帧动画
        mBombImage.setBackgroundResource(R.drawable.anim_bubble_pop)

        val animationDrawable = mBombImage.background as AnimationDrawable
        mBombImage.x = pointF.x - animationDrawable.intrinsicWidth/2
        mBombImage.y = pointF.y - animationDrawable.intrinsicHeight/2
        animationDrawable.start()

        mBombImage.postDelayed({
            //移除爆炸View
            mWindowManager.removeView(mBombFrame)
            //移除拖动的View
            mDragView.visibility = View.GONE
            //通知监听者动画已经移除
            mDisappearListener?.disappear()
        },getAnimationDrawableTime(animationDrawable))
    }


    /**
     *  获取动画要执行的实际
     */
    private fun getAnimationDrawableTime(animationDrawable: AnimationDrawable): Long {
        val numberOfFrames = animationDrawable.numberOfFrames
        var time = 0L
        for(i in 0 until numberOfFrames){
            time += animationDrawable.getDuration(i)
        }
        return time
    }


}