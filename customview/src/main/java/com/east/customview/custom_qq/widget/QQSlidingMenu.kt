package com.east.customview.custom_qq.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.HorizontalScrollView
import android.widget.RelativeLayout
import com.east.customview.R
import kotlin.math.abs

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 仿酷狗侧滑效果
 *  @author: East
 *  @date: 2019-11-27
 * |---------------------------------------------------------------------------------------------------------------|
 */
class QQSlidingMenu @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : HorizontalScrollView(context, attrs, defStyleAttr) {

    //菜单栏的宽度
    private var mMenuWidth: Float
    private lateinit var mMenuView: View
    private lateinit var mContentView: View
    private lateinit var mShadowView : View //阴影View
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
        var typedArray = context.obtainStyledAttributes(attrs, R.styleable.QQSlidingMenu)
        var rightMenuMargin =
                typedArray.getDimension(R.styleable.QQSlidingMenu_qqRightMenuMargin, dip2px(40f) * 10f / 7f)
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

        // 把内容布局单独提取出来，
        mContentView = container.getChildAt(1)
        val contentParams = mContentView.layoutParams
        container.removeView(mContentView)
        // 然后在外面套一层阴影，
        var contentContainer = RelativeLayout(context)
        contentContainer.addView(mContentView)
        mShadowView = View(context)
        mShadowView.setBackgroundColor(Color.parseColor("#88000000"))
        contentContainer.addView(mShadowView)
        // 最后在把容器放回原来的位置
        contentParams.width = getScreenWidth(context)
        contentContainer.layoutParams = contentParams
        container.addView(contentContainer)
        mShadowView.alpha = 0.0f //一开始是这个半透明遮罩是透明的

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

        mShadowView.alpha = 1 - scale


        // 最后一个效果 退出这个按钮刚开始是在右边，安装我们目前的方式永远都是在左边
        // 设置平移，先看一个抽屉效果
        // ViewCompat.setTranslationX(mMenuView,l);
        // 平移 l*0.7f
        mMenuView.translationX = 0.6f * l
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