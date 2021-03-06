package com.east.customview.custom_kugou.widget

import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.HorizontalScrollView
import com.east.customview.R
import kotlin.math.abs

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 仿酷狗侧滑效果
 *  @author: East
 *  @date: 2019-11-27
 * |---------------------------------------------------------------------------------------------------------------|
 */
class SlidingMenu @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : HorizontalScrollView(context, attrs, defStyleAttr) {

    //菜单栏的宽度
    private var mMenuWidth: Float
    private lateinit var mMenuView: View
    private lateinit var mContentView: View
    private var mIsMenuOpen = false //菜单是否是打开的状态

    private var mInterceptor = false // 是否拦截事件

    // GestureDetector 处理快速滑动
    private var mGestureDetector: GestureDetector

    private var mOnGestureListener: GestureDetector.SimpleOnGestureListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
            Log.e("TAG","onFling -- $velocityX")
            if(mIsMenuOpen){
                if(velocityX<0 && abs(velocityX) > abs(velocityY)){
                    closeMenu()
                    return true
                }
            }else{
                if(velocityX>0 && abs(velocityX) > abs(velocityY)){
                    openMenu()
                    return true
                }
            }
            return super.onFling(e1, e2, velocityX, velocityY)
        }
    }

    init {
        var typedArray = context.obtainStyledAttributes(attrs, R.styleable.SlidingMenu)
        var rightMenuMargin =
                typedArray.getDimension(R.styleable.SlidingMenu_rightMenuMargin, dip2px(40f) * 10f / 7f)
        // 菜单页的宽度是 = 屏幕的宽度 - 右边的一小部分距离（自定义属性）
        mMenuWidth = getScreenWidth(context) - rightMenuMargin
        typedArray.recycle()

        mGestureDetector = GestureDetector(context, mOnGestureListener)
    }

    /**
     *  1. 在这里为子View指定宽度
     *
     *  这个方法是布局解析完毕也就是 XML 布局文件解析完毕,在onCreate中调用
     */
    override fun onFinishInflate() {
        super.onFinishInflate()

        //获取LinearLayout
        val container = getChildAt(0) as ViewGroup

        val childCount = container.childCount
        if (childCount != 2) {
            throw RuntimeException("只能放置两个子View!")
        }

        //1.为菜单View指定宽度
        mMenuView = container.getChildAt(0)
        val menuParams = mMenuView.layoutParams
        menuParams.width = mMenuWidth.toInt()
        //7.0以下的手机需要采用以下方式
        mMenuView.layoutParams = menuParams

        //1.为contentView指定宽度
        mContentView = container.getChildAt(1)
        val contentParams = mContentView.layoutParams
        contentParams.width = getScreenWidth(context)
        //7.0以下的手机需要采用以下方式
        mContentView.layoutParams = contentParams

    }

    /**
     *  2.启动时关闭菜单
     *  在onResume之后调用
     */
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        //这种滚动是没动画的
        scrollTo(mMenuWidth.toInt(), 0)
    }

    //6. 处理事件拦截 + ViewGroup 事件分发的源码实践
    //        //    当菜单打开的时候，手指触摸右边内容部分需要关闭菜单，还需要拦截事件（打开情况下点击内容页不会响应点击事件）
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        Log.e("TAG","${ev.action}")
        mInterceptor = false
        //菜单打开的情况下
        if(mIsMenuOpen){
            if(ev.x > mMenuWidth){
                //6.1关闭菜单
                closeMenu()
                //6.2.子 View 不需要响应任何事件（点击和触摸），拦截子 View 的事件
                //如果返回 true 代表我会拦截子View的事件，但是我会响应自己的 onTouch 事件
                mInterceptor = true
                return true
            }
        }
        return super.onInterceptTouchEvent(ev)
    }


    /**
     * 3.手指抬起是二选一，要么关闭要么打开
     */
    override fun onTouchEvent(ev: MotionEvent): Boolean {

        //如果有拦截不执行自己的onTouch事件
        if(mInterceptor)
            return true

        //5.判断是否是快速滑动,如果是快速滑动,则先响应快速滑动的事件,如果快速滑动没处理则交由后面的逻辑去处理
        if(mGestureDetector.onTouchEvent(ev)){
            return true
        }

        // 1. 获取手指滑动的速率，当期大于一定值就认为是快速滑动 ， GestureDetector（系统提供好的类）
        // 2. 处理事件拦截 + ViewGroup 事件分发的源码实践
        //    当菜单打开的时候，手指触摸右边内容部分需要关闭菜单，还需要拦截事件（打开情况下点击内容页不会响应点击事件）

        if (ev.action == MotionEvent.ACTION_UP) {
            // 只需要管手指抬起 ，根据我们当前滚动的距离来判断
            if (scrollX > mMenuWidth / 2)
                closeMenu()
            else
                openMenu()

            return true //确保super.onTouchEvent()里面的fling不执行
        }
        return super.onTouchEvent(ev)
    }

    /**
     *  4.设置缩放和透明
     */
    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        Log.e("TAG", "l -> $l") // 变化是 mMenuWidth - 0

        // 算一个梯度值
        var scale = l / mMenuWidth //scale变化时 1 - 0
        // 右边的缩放: 最小是 0.7f, 最大是 1f
        val rightScale = 0.7f + 0.3f * scale
        // 设置右边的缩放,默认是以中心点缩放
        // 设置缩放的中心点位置
        mContentView.pivotX = 0f
        mContentView.pivotY = (measuredHeight / 2).toFloat()
        mContentView.scaleX = rightScale
        mContentView.scaleY = rightScale

        //设置左边的透明度和缩放

        // 右边的缩放: 最小是 0.7f, 最大是 1f
        val leftScale = 0.7f + 0.3f * (1 - scale)
        mMenuView.scaleX = leftScale
        mMenuView.scaleY = leftScale
        //设置透明度
        var leftAlpha = 0.1f + 0.9 * (1 - scale)
        mMenuView.alpha = leftAlpha.toFloat()


        // 最后一个效果 退出这个按钮刚开始是在右边，安装我们目前的方式永远都是在左边
        // 设置平移，先看一个抽屉效果
        // ViewCompat.setTranslationX(mMenuView,l);
        // 平移 l*0.7f
        mMenuView.translationX = 0.25f * l
    }

    /**
     * 打开菜单
     */
    fun openMenu() {
        smoothScrollTo(0, 0)
        mIsMenuOpen = true
    }

    /**
     * 关闭菜单
     */
    fun closeMenu() {
        smoothScrollTo(mMenuWidth.toInt(), 0)
        mIsMenuOpen = false
    }


    /**
     *  获取屏幕的宽度
     */
    fun getScreenWidth(context: Context): Int {
        var windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        var outMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.widthPixels
    }

    /**
     *  dp 转 px
     */
    fun dip2px(dpValue: Float): Float {
        var scale = resources.displayMetrics.density
        return dpValue * scale + 0.5f
    }


}