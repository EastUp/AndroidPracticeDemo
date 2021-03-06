package com.east.customview.huashu_loading.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.RelativeLayout

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 花束直播加载动画
 *  @author: East
 *  @date: 2019-12-30
 * |---------------------------------------------------------------------------------------------------------------|
 */
class LoadingView @JvmOverloads constructor(
    context: Context,
    attrs : AttributeSet ?= null,
    defStyleAttr : Int = 0
) :RelativeLayout(context,attrs, defStyleAttr){
    private var mLeftView = CirCleView(context)
    private var mMiddleView = CirCleView(context)
    private var mRightView = CirCleView(context)
    //动画移动的距离
    private var mTranslationDistance = 30f
    //动画执行的时间
    private var mAnimatorDuration = 350L
    //圆的大小
    var mCirCleSize = 10f
    //是否停止播放
    var mStop = false
    private var mExpandAnimatorSet:AnimatorSet
    private var mShrinkAnimatorSet:AnimatorSet
    init {
        //1.把圆添加到布局中
        mLeftView.changeColor(Color.RED)
        mMiddleView.changeColor(Color.BLUE)
        mRightView.changeColor(Color.GREEN)
        var params = LayoutParams(dip2px(mCirCleSize).toInt(),dip2px(mCirCleSize).toInt())
        params.addRule(CENTER_IN_PARENT)
        mLeftView.layoutParams = params
        mMiddleView.layoutParams = params
        mRightView.layoutParams = params
        addView(mLeftView)
        addView(mRightView)
        addView(mMiddleView) //中间的小圆要处在最顶层


        var leftExpandAnimator = ObjectAnimator.ofFloat(mLeftView,"translationX",0f,-mTranslationDistance)
        var rightExpandAnimator = ObjectAnimator.ofFloat(mRightView,"translationX",0f,mTranslationDistance)
        mExpandAnimatorSet = AnimatorSet()
        mExpandAnimatorSet.interpolator = DecelerateInterpolator()
        mExpandAnimatorSet.duration = mAnimatorDuration
        mExpandAnimatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                //3.收缩动画
                shrinkAnimator()
            }
        })
        mExpandAnimatorSet.playTogether(leftExpandAnimator,rightExpandAnimator)

        var leftShrinkAnimator = ObjectAnimator.ofFloat(mLeftView,"translationX",-mTranslationDistance,0f)
        var rightShrinkAnimator = ObjectAnimator.ofFloat(mRightView,"translationX",mTranslationDistance,0f)
        mShrinkAnimatorSet = AnimatorSet()
        mShrinkAnimatorSet.duration = mAnimatorDuration
        mShrinkAnimatorSet.interpolator = AccelerateInterpolator()
        mShrinkAnimatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                //4.改变颜色
                changeColor()
                //5.展开动画
                expandAnimator()
            }
        })
        mShrinkAnimatorSet.playTogether(leftShrinkAnimator,rightShrinkAnimator)

        post {
            //2.开启展开动画
            expandAnimator()
        }
    }

    /**
     * 展开动画(减速)
     */
    private fun expandAnimator() {
        if(mStop)
            return
        Log.d("TAG","expandAnimator")
        mExpandAnimatorSet.start()
    }

    /**
     *  收缩动画(加速)
     */
    private fun shrinkAnimator() {
        if(mStop)
            return

        mShrinkAnimatorSet.start()
    }

    /**
     *  每次收缩回来过后,颜色需要变换
     */
    fun changeColor(){
        var color = mLeftView.mColor
        mLeftView.changeColor(mMiddleView.mColor)
        mMiddleView.changeColor(mRightView.mColor)
        mRightView.changeColor(color)
    }

    /**
     *  控制动画执行,防止内存溢出
     */
    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)
        if(visibility == View.VISIBLE){
            mLeftView.translationX = 0f
            mRightView.translationX = 0f
            mStop = false
            expandAnimator() //显示的时候再展开动画
        }else{
            mStop = true
            mLeftView.clearAnimation()
            mRightView.clearAnimation()
        }
    }

    private fun dip2px(dip: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dip,resources.displayMetrics)
}