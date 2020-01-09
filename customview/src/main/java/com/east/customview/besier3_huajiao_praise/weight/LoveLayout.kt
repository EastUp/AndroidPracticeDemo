package com.east.customview.besier3_huajiao_praise.weight

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.PointF
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.east.customview.R
import com.east.customview.besier3_huajiao_praise.LoveTypeEvaluator
import kotlin.properties.Delegates
import kotlin.random.Random

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 花椒直播点赞效果
 *  @author: East
 *  @date: 2020-01-08
 * |---------------------------------------------------------------------------------------------------------------|
 */
class LoveLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    //图片资源
    var mImageRes: IntArray =
        intArrayOf(R.drawable.pl_blue, R.drawable.pl_red, R.drawable.pl_yellow)
    //控件宽高
    var mWidth by Delegates.notNull<Int>()
    var mHeight by Delegates.notNull<Int>()
    //图片宽高
    var mDrawableWidth by Delegates.notNull<Int>()
    var mDrawableHeight by Delegates.notNull<Int>()

    //插值器
    private val mInterpolator = arrayOf(
        AccelerateDecelerateInterpolator(), AccelerateInterpolator(),
        DecelerateInterpolator(), LinearInterpolator()
    )

    init {
        var drawable = ContextCompat.getDrawable(context, R.drawable.pl_blue)
        mDrawableWidth = drawable!!.intrinsicWidth
        mDrawableHeight = drawable.intrinsicHeight
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = MeasureSpec.getSize(widthMeasureSpec)
        mHeight = MeasureSpec.getSize(heightMeasureSpec)
    }

    /**
     *  添加点赞效果
     */
    fun addLove() {
        // 添加一个ImageView在底部
        var loveIv = ImageView(context)
        loveIv.setImageResource(mImageRes[Random.nextInt(mImageRes.size - 1)])
        var params = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.addRule(ALIGN_PARENT_BOTTOM)
        params.addRule(CENTER_HORIZONTAL)
        loveIv.layoutParams = params
        addView(loveIv)

        var animatorSet = getAnimatorSet(loveIv)
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                removeView(loveIv)
            }
        })
        animatorSet.start()
    }

    private fun getAnimatorSet(loveIv: ImageView): AnimatorSet {
        val allAnimatorSet = AnimatorSet() // 待会再用

        // 添加的效果：有放大和透明度变化 （属性动画）
        val innerAnimator = AnimatorSet()
        var alphaAnimator = ObjectAnimator.ofFloat(loveIv, "alpha", 0.3f, 1f)
        var scaleXAnimator = ObjectAnimator.ofFloat(loveIv, "scaleX", 0.3f, 1f)
        var scaleYAnimator = ObjectAnimator.ofFloat(loveIv, "scaleY", 0.3f, 1f)
        innerAnimator.playTogether(alphaAnimator, scaleXAnimator, scaleYAnimator)
        innerAnimator.duration = 350
        // 运行的路径动画  playSequentially 按循序执行
        allAnimatorSet.playSequentially(innerAnimator, getBesierAnimator(loveIv))
        return allAnimatorSet
    }

    /**
     *  获取贝塞尔运动路径动画
     */
    private fun getBesierAnimator(loveIv: ImageView): Animator {
        // 怎么确定四个点
        var point0 = PointF(
            (mWidth / 2 - mDrawableWidth / 2).toFloat(),
            (mHeight - mDrawableHeight).toFloat()
        )
        // 确保 p2 点的 y 值 一定要小于 p1 点的 y 值
        var point1 = getPoint(1)
        var point2 = getPoint(2)
        var point3 = PointF(Random.nextInt(mWidth - mDrawableWidth).toFloat(), 0f)

        var loveTypeEvaluator = LoveTypeEvaluator(point1, point2)
        // ofObject  第一个参数 LoveTypeEvaluator 第二个参数 p0, 第三个是 p3
        var animator = ObjectAnimator.ofObject(loveTypeEvaluator, point0, point3)
        // 加一些随机的差值器（效果更炫）
        animator.interpolator = mInterpolator[Random.nextInt(mInterpolator.size - 1)]
        animator.duration = 1500
        animator.addUpdateListener {
            var pointF = it.animatedValue as PointF
            loveIv.x = pointF.x
            loveIv.y = pointF.y
            // 透明度
            val t = it.animatedFraction //0-1
            loveIv.alpha = 1 - t + 0.2f
        }
        return animator
    }

    private fun getPoint(index: Int): PointF = PointF(
        Random.nextInt(mWidth - mDrawableWidth).toFloat(),
        (Random.nextInt(mHeight / 2) + (index - 1) * mHeight / 2).toFloat()
    )
}