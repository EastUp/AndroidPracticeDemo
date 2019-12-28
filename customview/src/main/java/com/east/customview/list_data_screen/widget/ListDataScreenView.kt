package com.east.customview.list_data_screen.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.east.customview.list_data_screen.MenuObserver
import com.east.customview.list_data_screen.adapter.BaseMenuAdapter

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 多条目菜单筛选
 *                布局 = LinearLayout = LinearLayout = TabViewContainer+MiddleView(ShadowView+ContentContainer)
 *  @author: East
 *  @date: 2019-12-28
 * |---------------------------------------------------------------------------------------------------------------|
 */
class ListDataScreenView @JvmOverloads constructor(
    context:Context,
    attrs:AttributeSet ?= null,
    defStyleAttr : Int = 0
) :LinearLayout(context,attrs, defStyleAttr){
    //用来存放头部的Tab
    var mTabViewContainer = LinearLayout(context)
    // 用来存放 = 阴影（View） + 菜单内容布局(FrameLayout)
    private var mMiddleView = FrameLayout(context)
    //用来存放阴影
    private var mShadowView = View(context)
    //用来存放内容
    private var mMenuContentContainer = FrameLayout(context)
    private var mShadowColor = Color.parseColor("#80000000")
    //内容的高度
    private var mMenuContentHeight = 0
    //通过Adapter的设计模式来填充数据和View
    var mAdapter : BaseMenuAdapter ?= null
    //内容部分打没打开的判断(当前打开的是position为几的内容,pisition = -1代表没打开内容)
    private var mCurrentPosition = -1
    private var mAnimatorDuration = 350L//动画执行的时间
    //动画是否正在执行中
    private var mIsAnimatorExcuting = false

    private var mObserver :MenuObserver ?= null//观察者
    init {
        orientation = VERTICAL //默认排列方式是竖直排列
        //1.用代码的方式把布局填充进去
        //1.1把头部添加到布局中
        mTabViewContainer.layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        mTabViewContainer.orientation = HORIZONTAL
        addView(mTabViewContainer)
        //1.2把底部内容加入到布局中
        mMiddleView.layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        addView(mMiddleView)
        // 创建阴影 可以不用设置 LayoutParams 默认就是 MATCH_PARENT ，MATCH_PARENT
        mShadowView.setBackgroundColor(mShadowColor)
        mShadowView.alpha = 0f
        mMiddleView.addView(mShadowView)
        mShadowView.setOnClickListener {
            closeMenuContent()//关闭菜单
        }
        mMenuContentContainer.setBackgroundColor(Color.BLACK)
        mMiddleView.addView(mMenuContentContainer)
        mMiddleView.visibility = View.GONE //一开始的时候底部是全部隐藏了的
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)
        if(mMenuContentHeight == 0){ //防止onMeasure方法调用多次
            //2.重新设置下ContentContainer的高度
            //内容的高度应该不是全部  应该是整个 View的 75%
            mMenuContentHeight = height/100*75
            mMenuContentContainer.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,mMenuContentHeight)
            mMenuContentContainer.translationY = -mMenuContentHeight.toFloat()
        }
    }



    /**
     *  设置Adapter View
     */
    fun setAdapter(adapter: BaseMenuAdapter){
        //先解除之前的观察者
        if(mAdapter!=null && mObserver!=null){
            mAdapter!!.unRegisterDataSetObserver(mObserver!!)
        }

        this.mAdapter = adapter
        //创建观察者,并注册
        mAdapter!!.registerDataSetObserver(object :MenuObserver{
            override fun closeMenuContent() {
                this@ListDataScreenView.closeMenuContent()
            }

        })

        val count = adapter.getItemCount()
        for(position in 0 until count){
            //3.添加tabview到TabViewContainer中去
            val tabView = adapter.getTabView(position, mTabViewContainer)
            mTabViewContainer.addView(tabView)
            val layoutParams = tabView.layoutParams as LayoutParams
            layoutParams.weight = 1f //让tabview居中
            tabView.layoutParams = layoutParams
            //5.为每个TabView加入点击事件
            setTabViewOnClick(position)

            //4.添加ContentView到ContentContainer中去
            val contentView = adapter.getContentView(position,mMenuContentContainer)
            mMenuContentContainer.addView(contentView)
            contentView.visibility = View.GONE //先全部隐藏
        }
    }

    /**
     *  TabView设置点击事件
     */
    private fun setTabViewOnClick(position: Int) {
        mTabViewContainer.getChildAt(position).setOnClickListener {
            if(mCurrentPosition == -1){
                openMenuContent(position)
            }else{
                if(position == mCurrentPosition)//菜单已经打开了,现在关闭
                    closeMenuContent()
                else{//代表打开的是其它的Position,这个时候不需要关闭菜单
                    mAdapter!!.closeMenu(mTabViewContainer.getChildAt(mCurrentPosition))
                    mMenuContentContainer.getChildAt(mCurrentPosition).visibility = View.GONE
                    mCurrentPosition = position
                    mMenuContentContainer.getChildAt(mCurrentPosition).visibility = View.VISIBLE
                    mAdapter!!.openMenu(mTabViewContainer.getChildAt(mCurrentPosition))
                }
            }
        }
    }

    /**
     *  打开菜单内容
     */
    private fun openMenuContent(position: Int) {
        if(mIsAnimatorExcuting)
            return
        mCurrentPosition = position
        mMiddleView.visibility = View.VISIBLE
        mMenuContentContainer.getChildAt(position).visibility = View.VISIBLE
        var menuContentAnimator = ObjectAnimator.ofFloat(mMenuContentContainer,"translationY",-mMenuContentHeight.toFloat(),0f)
        var shadowAnimator = ObjectAnimator.ofFloat(mShadowView,"alpha",0f,1f)
        var animatorSet = AnimatorSet()
        animatorSet.playTogether(menuContentAnimator,shadowAnimator)
        animatorSet.duration = mAnimatorDuration
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                mIsAnimatorExcuting = true
                mAdapter!!.openMenu(mTabViewContainer.getChildAt(mCurrentPosition))
            }

            override fun onAnimationEnd(animation: Animator?) {
                mIsAnimatorExcuting = false
            }
        })
        animatorSet.start()

    }

    /**
     *  关闭菜单内容
     */
    private fun closeMenuContent() {
        if(mIsAnimatorExcuting)
            return
        var menuContentAnimator = ObjectAnimator.ofFloat(mMenuContentContainer,"translationY",0f,-mMenuContentHeight.toFloat())
        var shadowAnimator = ObjectAnimator.ofFloat(mShadowView,"alpha",1f,0f)
        var animatorSet = AnimatorSet()
        animatorSet.playTogether(menuContentAnimator,shadowAnimator)
        animatorSet.duration = mAnimatorDuration
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                mMenuContentContainer.getChildAt(mCurrentPosition).visibility = View.GONE
                mMiddleView.visibility = View.GONE
                mCurrentPosition = -1 //关闭完菜单后把当前选中的position置为-1 代表菜单没打开
                mIsAnimatorExcuting = false
            }

            override fun onAnimationStart(animation: Animator?) {
                mIsAnimatorExcuting = true
                mAdapter!!.closeMenu(mTabViewContainer.getChildAt(mCurrentPosition))
            }
        })
        animatorSet.start()
    }
}