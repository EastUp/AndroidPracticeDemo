package com.east.customview.custom_redpacket.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.east.customview.R

/**
 * |---------------------------------------------------------------------------------------------------------------|
 * @description:  抢红包动效
 * @author: East
 * @date: 2020-01-17 11:47
 * |---------------------------------------------------------------------------------------------------------------|
 */
class RedPackageView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val mRedPackageBitmap // 红包
            = BitmapFactory.decodeResource(
        resources,
        R.drawable.icon_game_red_package_normal
    )
    private val mProgressBgBitmap // 进度条背景
            = BitmapFactory.decodeResource(
        resources,
        R.drawable.icon_game_red_package_pb_bg
    )
    // 进度定义
    private var mTotalProgress = 2f
    private var mCurrentProgress = 1f
    // 颜色渐变
    private val mProgressStarColor = Color.parseColor("#FDA501")
    private val mProgressEndColor = Color.parseColor("#FFEF74")
    private val mProgressPaint: Paint
    // 爆炸的个数
    private val mBombNumber = 8
    // 爆炸的icon
    private val mBombIcon =
        arrayOfNulls<Bitmap>(8)
    // 爆炸最大半径
    private var mTotalBombRadius = 0f
    private var mCurrentBombProgress = 0f// 当前爆炸的进度 = 0f
    private val mBombPaint: Paint
    private var mProgressAnimator: ValueAnimator? = null

    init {
        mTotalBombRadius = mProgressBgBitmap.height * 0.7f
        val bomb1 = BitmapFactory.decodeResource(
            resources,
            R.drawable.icon_red_package_bomb_1
        )
        val bomb2 = BitmapFactory.decodeResource(
            resources,
            R.drawable.icon_red_package_bomb_2
        )
        mBombIcon[0] = bomb1
        mBombIcon[1] = bomb2
        mProgressPaint = Paint()
        mProgressPaint.isDither = true
        mProgressPaint.isAntiAlias = true
        mBombPaint = Paint()
        mBombPaint.isDither = true
        mBombPaint.isAntiAlias = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 指定宽高? 为了适配爆炸效果
        val size = (Math.max(
            mRedPackageBitmap.width,
            mRedPackageBitmap.height
        ) * 1.2f).toInt()
        setMeasuredDimension(size, size)
    }

    override fun onDraw(canvas: Canvas) {
        val height = height
        val width = width
        // 画红包
        canvas.drawBitmap(mRedPackageBitmap, 0f, 0f, null)
        // 画背景 调整（适配，源码的熟悉）算比例
        var top = (mRedPackageBitmap.height - mProgressBgBitmap.height * 0.8f).toInt()
        var left = (mProgressBgBitmap.width * 0.08f).toInt()
        canvas.drawBitmap(mProgressBgBitmap, left.toFloat(), top.toFloat(), null)
        if (mTotalProgress == 0f) {
            return
        }
        var bombCenterX = 0
        var bombCenterY = 0
        if (mCurrentProgress != 0f) { // 画进度 totalProgress currentProgress
            val progressWidth = (mProgressBgBitmap.width * 0.78f).toInt()
            val currentProgressWidth =
                (progressWidth * mCurrentProgress / mTotalProgress).toInt()
            val progressHeight = (mProgressBgBitmap.height * 0.3f).toInt()
            left = (mProgressBgBitmap.width * 0.2f).toInt()
            top = (height * 0.64f).toInt()
            val rectF =
                RectF(
                    left.toFloat(),
                    top.toFloat(),
                    (left + currentProgressWidth).toFloat(),
                    (top + progressHeight).toFloat()
                )
            val round = progressHeight / 2
            // 设置进度条渐变颜色
            val shader: Shader = LinearGradient(
                0f,
                0f,
                currentProgressWidth.toFloat(),
                0f,
                intArrayOf(mProgressStarColor, mProgressEndColor),
                floatArrayOf(0f, 1.0f),
                Shader.TileMode.CLAMP
            )
            mProgressPaint.shader = shader
            canvas.drawRoundRect(rectF, round.toFloat(), round.toFloat(), mProgressPaint)
            bombCenterX = currentProgressWidth + left
            bombCenterY = top - progressHeight / 2
        }
        if (mCurrentBombProgress > 0 && mCurrentBombProgress < 1) { // 爆炸的动画，准备几个 Bitmap for循环，计算半径
            val preAngle = (2 * Math.PI / mBombNumber).toFloat()
            mBombPaint.alpha = (300 - mCurrentBombProgress * 255).toInt() // 0~255
            for (i in 0 until mBombNumber) { // 初始角度 + 当前旋转的角度
                val angle = i * preAngle.toDouble()
                val mCurrentBombRadius = mTotalBombRadius * mCurrentBombProgress
                val cx =
                    (bombCenterX + mCurrentBombRadius * Math.cos(angle)).toFloat()
                val cy =
                    (bombCenterY + mCurrentBombRadius * Math.sin(angle)).toFloat()
                val bombBitmap = mBombIcon[i % 2]
                canvas.drawBitmap(bombBitmap!!, cx - bombBitmap.width / 2, cy, mBombPaint)
            }
        }
    }

    @Synchronized
    private fun setCurrentProgress(currentProgress: Float) {
        mCurrentProgress = currentProgress
        invalidate()
    }

    fun setTotalProgress(totalProgress: Int) {
        mTotalProgress = totalProgress.toFloat()
    }

    fun startAnimation(from: Int, to: Int) { // 1,2
        if (mProgressAnimator == null) {
            mProgressAnimator =
                ValueAnimator.ofFloat(from.toFloat(), to.toFloat())
            mProgressAnimator!!.duration = 600
            mProgressAnimator!!.addUpdateListener(ValueAnimator.AnimatorUpdateListener { animation ->
                val current = animation.animatedValue as Float
                setCurrentProgress(current)
            })
            // 我们的进度条涨满之后，就可以开始执行我们的扩散爆炸
            mProgressAnimator!!.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    executeBombAnimator()
                }
            })
        }
        mProgressAnimator!!.start()
    }

    /**
     * 执行扩散动画
     */
    private fun executeBombAnimator() {
        val animator =
            ValueAnimator.ofFloat(0f, 1f)
        animator.addUpdateListener { animation ->
            mCurrentBombProgress = animation.animatedValue as Float
            invalidate()
        }
        animator.start()
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                if (mCurrentProgress == mTotalProgress) { // 缩小放大 ，执行喷洒（Lottie ，帧动画）
                }
            }
        })
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        // 做最后处理，再去看下内存泄漏
// LottieAnimationView 小坑（性能），不要反复去设置 addListener 10次 ANR
    }
}