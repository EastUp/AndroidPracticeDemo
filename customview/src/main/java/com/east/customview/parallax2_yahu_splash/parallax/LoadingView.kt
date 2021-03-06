package com.east.customview.parallax2_yahu_splash.parallax

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.AnticipateInterpolator
import android.view.animation.LinearInterpolator
import com.east.customview.R
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:  视差动画仿雅虎加载(π = 180度)
 *  @author: East
 *  @date: 2020-01-10
 * |---------------------------------------------------------------------------------------------------------------|
 */
class LoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    //1.6个圆旋转
    //2.聚合动画(先扩展再收缩)
    //3.扩展动画(内容逐渐显示出来)

    //当前所处的加载状态
    private var mLoadingState: LoadingState? = null
    //动画执行的时间
    private var ROTATION_ANIMATION_TIME = 1000L
    //是否初始化宽度/半径之类的
    private var mInitParams = false
    //背景色
    private var mSplashColor = Color.WHITE
    private var mPaint = Paint()
    //当前旋转的角度
    private var mCurrentRotationAngle: Float = 0f
    //大圆半径(屏幕宽度的1/4)
    private var mRotationRadius: Float = 0f
    private var mCurrentRotationRadius = mRotationRadius
    //校园半径(大圆半径的1/8)
    private var mCircleRadius: Float = 0f
    // 屏幕对角线的一半
    private var mDiagonalDist = 0f
    private lateinit var mCircleColors: IntArray

    init {
        mPaint.isAntiAlias = true //抗锯齿
        mPaint.isDither = true //仿抖动
    }

    override fun onDraw(canvas: Canvas) {
        if (!mInitParams) {
            initParams()
        }

        /**
         * 当不上扩展动画的时候canvas背景才画成白色
         */
        if(mLoadingState !is ExpandState){
            //画一个背景  白色
            canvas.drawColor(mSplashColor)
        }
        //开始旋转动画
        if (mLoadingState == null) {
            mLoadingState = RotationState()
        }

        mLoadingState!!.draw(canvas)

    }

    /**
     *  初始化参数
     */
    private fun initParams() {
        mCircleColors = resources.getIntArray(R.array.splash_circle_colors)
        mRotationRadius = (measuredWidth / 4).toFloat()
        mCircleRadius = mRotationRadius / 8
        mDiagonalDist = sqrt(((measuredWidth / 2).toDouble()).pow(2)+((measuredHeight/2).toDouble()).pow(2)).toFloat()
        mInitParams = true
    }

    /**
     *  数据加载完毕后进行扩展动画展开
     */
    fun disappear() {
        if (mLoadingState is RotationState) {
            (mLoadingState as RotationState).cancel()
            mLoadingState = MergeState()
        }
    }


    /**
     *  当前加载的状态
     */
    abstract class LoadingState {
        abstract fun draw(canvas: Canvas)
    }

    /**
     *  旋转动画
     */
    inner class RotationState : LoadingState() {
        var mAnimator: ValueAnimator = ObjectAnimator.ofFloat(0f, 2 * Math.PI.toFloat())

        init {
            mAnimator.addUpdateListener {
                mCurrentRotationAngle = it.animatedValue as Float
                //重新绘制
                invalidate()
            }
            mAnimator.duration = ROTATION_ANIMATION_TIME
            mAnimator.interpolator = LinearInterpolator()
            mAnimator.repeatCount = -1
            mAnimator.start()
        }

        override fun draw(canvas: Canvas) {
            //Math.PI代表180度,360度平分成6分
            var percentAngle = Math.PI * 2 / mCircleColors.size
            for ((index, color) in mCircleColors.withIndex()) {
                mPaint.color = color
                var currentAngle = index * percentAngle + mCurrentRotationAngle
                var cx = measuredWidth / 2 + mRotationRadius * sin(currentAngle)
                var cy = measuredHeight / 2 - mRotationRadius * cos(currentAngle)
                canvas.drawCircle(cx.toFloat(), cy.toFloat(), mCircleRadius, mPaint)
            }
        }

        /**
         *  停止旋转动画
         */
        fun cancel() {
            mAnimator.cancel()
        }
    }

    /**
     * 聚合动画(先扩展再收缩)
     */
    inner class MergeState : LoadingState() {

        private var mAnimator = ObjectAnimator.ofFloat(mRotationRadius, 0f)

        init {
            mAnimator.addUpdateListener {
                mCurrentRotationRadius = it.animatedValue as Float
                //重新绘制
                invalidate()
            }
            mAnimator.duration = ROTATION_ANIMATION_TIME/2
            // 开始的时候向后然后向前甩
            mAnimator.interpolator = AnticipateInterpolator(5f)
            //等聚合动画完毕后展开
            mAnimator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    mLoadingState = ExpandState()
                }
            })
            mAnimator.start()
        }

        override fun draw(canvas: Canvas) {
            //Math.PI代表180度,360度平分成6分
            var percentAngle = Math.PI * 2 / mCircleColors.size
            for ((index, color) in mCircleColors.withIndex()) {
                mPaint.color = color
                var currentAngle = index * percentAngle + mCurrentRotationAngle
                var cx = measuredWidth / 2 + mCurrentRotationRadius * sin(currentAngle)
                var cy = measuredHeight / 2 - mCurrentRotationRadius * cos(currentAngle)
                canvas.drawCircle(cx.toFloat(), cy.toFloat(), mCircleRadius, mPaint)
            }
        }
    }

    /**
     * 扩展动画
     */
    inner class ExpandState : LoadingState(){
        /**
         * 圆的半径需要从
         */
        private var mAnimator = ObjectAnimator.ofFloat(0f,mDiagonalDist)
        private var radius = 0f
        init {
            mAnimator.addUpdateListener {
                //半径 需要 + 画笔宽度的一半
                radius = it.animatedValue as Float + mDiagonalDist/2
                invalidate()
            }
            mAnimator.duration = ROTATION_ANIMATION_TIME/2
            mAnimator.addListener(object :AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator?) {
                    visibility = GONE
                }
            })
            mAnimator.start()
        }

        override fun draw(canvas: Canvas) {
            mPaint.color = Color.WHITE
            mPaint.style = Paint.Style.STROKE
            mPaint.strokeWidth = mDiagonalDist
            canvas.drawCircle((measuredWidth/2).toFloat(), (measuredHeight/2).toFloat(),radius,mPaint)
        }
    }


}