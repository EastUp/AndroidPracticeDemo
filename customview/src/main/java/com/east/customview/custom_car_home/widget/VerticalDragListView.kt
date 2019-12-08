package com.east.customview.custom_car_home.widget

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.AbsListView
import android.widget.FrameLayout
import androidx.customview.widget.ViewDragHelper

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 汽车之家可折叠的控件
 *  @author: East
 *  @date: 2019-12-05
 * |---------------------------------------------------------------------------------------------------------------|
 */
class VerticalDragListView @JvmOverloads constructor(
    context: Context,
    attrs : AttributeSet ?= null,
    defStyleAttr : Int = 0
) :FrameLayout(context,attrs,defStyleAttr){

    //系统给我们写好的一个拖动工具类
    private var mViewDragHelper : ViewDragHelper ?=null

    private lateinit var mDragListView :View //可以拖动的View

    private var mMenuHeight : Int = 0 //菜单的高度,只能从0拖动到菜单的高度

    private var mMenuOpen = false //菜单是否打开

    private var mDownY = 0f

    private var mDragHelperCallBack = object : ViewDragHelper.Callback() {
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            //1.指定只有listView可以拖动
            return mDragListView == child
        }

//        //继承这个方法才能水平拖动
//        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
//            return left
//        }

        //继承这个方法才能垂直拖动
        //2.只能垂直方向拖动
        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            if(top < 0)
                return 0
            else if(top > mMenuHeight)
                return mMenuHeight
            return top
        }

        //3.手指松开的时候,只能选择把菜单遮住还是不遮住
        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            //yvel代表的是y轴的速率
            Log.e("TAG", "yvel -> $yvel mMenuHeight -> $mMenuHeight releasedChild -> $releasedChild")
            Log.e("TAG", "top -> " + mDragListView.top)
            //注意这里获取到的yvel是空,所以可以通过获取mDragListView的top来判断
            val top = mDragListView.top
            if(top > mMenuHeight/2){
                // 滚动到菜单的高度（打开）
                mMenuOpen = true
                mViewDragHelper?.settleCapturedViewAt(0,mMenuHeight) // 把拖动的View动画的移动到菜单的下面
            }
            else{
                // 滚动到0的位置（关闭）
                mMenuOpen = false
                mViewDragHelper?.settleCapturedViewAt(0,0)
            }
            invalidate() //必须要刷新才行
        }
    }

    init {
        mViewDragHelper = ViewDragHelper.create(this,mDragHelperCallBack)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if(childCount != 2){
            throw RuntimeException("VerticalDragListView 只能包含两个子布局")
        }
        mDragListView = getChildAt(1)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        mMenuHeight = getChildAt(0).measuredHeight
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        // 菜单打开要拦截
        if (mMenuOpen) {
            return true
        }

        val y = ev.y
        when(ev.action){
            MotionEvent.ACTION_DOWN -> {
                mDownY = y
                mViewDragHelper?.processTouchEvent(ev) // 把down事件传递给ViewDragHelper
            }

            MotionEvent.ACTION_MOVE -> {
                Log.d("TAG","y -> $y   y - mDownY  -> ${y - mDownY}")
                if((y - mDownY > 0 && !canChildScrollUp()))
                    return true
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mViewDragHelper?.processTouchEvent(event)
        return true
    }

    override fun computeScroll() {
        if(mViewDragHelper !=null && mViewDragHelper!!.continueSettling(true))
            invalidate()
    }


    /**
     *  判断View是否滚动到了最顶部,还能不能向上滚
     */
    fun canChildScrollUp(): Boolean {
        return if (Build.VERSION.SDK_INT < 14) {
            if (mDragListView is AbsListView) {
                val absListView =
                    mDragListView as AbsListView
                (absListView.childCount > 0
                        && (absListView.firstVisiblePosition > 0 || absListView.getChildAt(0)
                    .top < absListView.paddingTop))
            } else {
                mDragListView.canScrollVertically(
                    -1
                ) || mDragListView.scrollY > 0
            }
        } else {
            mDragListView.canScrollVertically(-1)
        }
    }

}