package com.east.customview.fang58.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import com.east.customview.R

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 仿58同城的数据加载并有动画
 *  @author: East
 *  @date: 2019-12-27
 * |---------------------------------------------------------------------------------------------------------------|
 */
class LoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var mShapeView: ShapeView // 图形
    private var mShadowView: View  //阴影
    private var mTranslationDistance: Float = 0f //下落距离
    private var mDuration = 350L //动画执行时间
    private var mIsStopAnimator = false //是否停止动画

    init {
        //1.把loadingview添加到这个自定义Group中
        val view = inflate(context, R.layout.ui_loading_view, this)
        mShapeView = view.findViewById(R.id.shape_view)
        mShadowView = view.findViewById(R.id.shadow_view)
        mTranslationDistance = dip2px(80f)
        if(visibility != View.VISIBLE) //如果在xml中就设置的是不可见那么就不开启动画
            mIsStopAnimator = true

        post {
            // onResume 之后View绘制流程执行完毕之后（View的绘制流程源码分析那一章）
            //2.开启下落和阴影缩小动画
            startFallAnimator()
        }

        // onCreate() 方法中执行 ，布局文件解析 反射创建实例
    }

    //2.下落和缩小动画
    private fun startFallAnimator() {
        if (mIsStopAnimator)
            return

        var fallAnimator =
            ObjectAnimator.ofFloat(mShapeView, "translationY", 0f, mTranslationDistance)
        var shapeScaleAnimator1 = ObjectAnimator.ofFloat(mShapeView, "scaleX", 1f, 0.3f)//X缩小
        var shapeScaleAnimator2 = ObjectAnimator.ofFloat(mShapeView, "scaleY", 1f, 0.3f)//Y缩小
        var shadowScaleAnimator = ObjectAnimator.ofFloat(mShadowView, "scaleX", 1f, 0.3f)//阴影只有X缩小

        var animatorSet = AnimatorSet()
//        animatorSet.playSequentially(fallAnimator,scaleAnimator)//按照顺序执行
        animatorSet.playTogether(
            fallAnimator,
            shapeScaleAnimator1,
            shapeScaleAnimator2,
            shadowScaleAnimator
        )//同时执行的动画
        animatorSet.duration = mDuration
        animatorSet.interpolator = AccelerateInterpolator() //下落的时候速度是慢慢加快的
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                //3.开启上抛和放大还有旋转的动画
                startUpAnimator()
                mShapeView.exchange()//切换图形
            }
        })
        animatorSet.start()
    }

    //3.开启上抛和放大还有旋转的动画
    private fun startUpAnimator() {
        if (mIsStopAnimator)
            return
        Log.e("TAG", "startUpAnimator$this")

        var fallAnimator =
            ObjectAnimator.ofFloat(mShapeView, "translationY", mTranslationDistance, 0f)
        var shapeScaleAnimator1 = ObjectAnimator.ofFloat(mShapeView, "scaleX", 0.3f, 1f)//X放大
        var shapeScaleAnimator2 = ObjectAnimator.ofFloat(mShapeView, "scaleY", 0.3f, 1f)//Y放大
        var shadowScaleAnimator = ObjectAnimator.ofFloat(mShadowView, "scaleX", 0.3f, 1f)//阴影只有X放大
        //4.根据不同的形状生成不同的旋转动画
        var rotationAnimator = generateRotationAnimator()
        var animatorSet = AnimatorSet()
//        animatorSet.playSequentially(fallAnimator,scaleAnimator)//按照顺序执行
        animatorSet.playTogether(
            fallAnimator,
            shapeScaleAnimator1,
            shapeScaleAnimator2,
            shadowScaleAnimator,
            rotationAnimator
        )//同时执行的动画
        animatorSet.duration = mDuration
        animatorSet.interpolator = DecelerateInterpolator() //上抛的时候速度慢慢降低
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                //5.下落和缩小动画
                startFallAnimator()
            }
        })
        animatorSet.start()
    }

    //4.根据不同的形状生成不同的旋转动画
    private fun generateRotationAnimator(): ObjectAnimator =
        when (mShapeView.mCurrentShape) {
            ShapeView.Shape.Circle, ShapeView.Shape.Square -> ObjectAnimator.ofFloat(
                mShapeView,
                "rotation",
                0f,
                180f
            )
            ShapeView.Shape.Tringle -> ObjectAnimator.ofFloat(mShapeView, "rotation", 0f, -120f)
        }

    fun dip2px(dip: Float) =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, resources.displayMetrics)

    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility) //不要再去摆放和计算，少走一些系统的源码（View的绘制流程）
        if(visibility == View.VISIBLE){
            mShapeView.translationY = 0f
            mShapeView.scaleX = 1f
            mShapeView.scaleY = 1f
            mShadowView.translationX = 1f
            mIsStopAnimator = false
            startFallAnimator() //显示的时候再展开动画
        }else{
            mIsStopAnimator = true
            // 清理动画
            mShapeView.clearAnimation()
            mShadowView.clearAnimation()
        }
//        //把loadingView从父布局中移除
//        val parent = parent as ViewGroup
//        if(parent!=null){
//            parent.removeView(this)// 从父布局移除
//            removeAllViews() // 移除自己所有的View
//        }

    }
}