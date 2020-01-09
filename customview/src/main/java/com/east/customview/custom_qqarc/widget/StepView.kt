package com.east.customview.custom_qqarc.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.east.customview.R


/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:  仿QQ运动步数
 *  @author: East
 *  @date: 2019-11-08
 * |---------------------------------------------------------------------------------------------------------------|
 */
class StepView @JvmOverloads constructor(
    context:Context,
    attrs: AttributeSet ?= null,
    defStyleAttr : Int = 0
):View(context,attrs,defStyleAttr) {

    private var mOuterColor:ColorStateList
    private var mInnerColor:ColorStateList
    private var mBorderWidth:Float = dp2px(8f)
    private var mTextColor:ColorStateList
    private var mTextSize:Float = sp2px(12f)

    private var mOuterPaint:Paint
    private var mInnerPaint:Paint
    private var mTextPaint:Paint


    private var mStepMax = 100
    private var mCurrentStep = 50

    init {
        val arr = context.obtainStyledAttributes(attrs, R.styleable.StepView)
        mOuterColor = arr.getColorStateList(R.styleable.StepView_outerColor)?: ColorStateList(
            arrayOf(drawableState), intArrayOf(Color.BLUE)
        )
        mInnerColor = arr.getColorStateList(R.styleable.StepView_innerColor)?: ColorStateList(
            arrayOf(drawableState), intArrayOf(Color.RED)
        )
        mBorderWidth = arr.getDimension(R.styleable.StepView_borderWidth,mBorderWidth)
        mTextColor = arr.getColorStateList(R.styleable.StepView_stepTextColor) ?: ColorStateList(
            arrayOf(drawableState), intArrayOf(Color.BLACK)
        )
        mTextSize = arr.getDimensionPixelSize(R.styleable.StepView_stepTextSize, mTextSize.toInt()).toFloat()
        mStepMax = arr.getInt(R.styleable.StepView_stepMax,mStepMax)
        mCurrentStep = arr.getInt(R.styleable.StepView_stepProgress,mCurrentStep)

        arr.recycle()

        mOuterPaint = Paint()
        mOuterPaint.isAntiAlias = true // 抗锯齿
        mOuterPaint.style = Paint.Style.STROKE //空心画笔
        mOuterPaint.strokeCap = Paint.Cap.ROUND //笔画以半圆突出,中心在路径的结尾端
        mOuterPaint.strokeWidth = mBorderWidth //设置笔画宽度
        mOuterPaint.color = mOuterColor.getColorForState(drawableState,0)//设置画笔颜色

        mInnerPaint = Paint()
        mInnerPaint.isAntiAlias = true // 抗锯齿
        mInnerPaint.style = Paint.Style.STROKE //空心画笔
        mInnerPaint.strokeCap = Paint.Cap.ROUND //笔画以半圆突出,中心在路径的结尾端
        mInnerPaint.strokeWidth = mBorderWidth //设置笔画宽度
        mInnerPaint.color = mInnerColor.getColorForState(drawableState,0)//设置画笔颜色

        mTextPaint = Paint()
        mTextPaint.isAntiAlias = true // 抗锯齿
        mTextPaint.textSize = mTextSize
        mTextPaint.color = mTextColor.getColorForState(drawableState,0)//设置画笔颜色

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //如果是AT_MOST模式证明是wrap_content则需要手动指定一个默认大小给到显示

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        var width = MeasureSpec.getSize(widthMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)

        if(widthMode == MeasureSpec.AT_MOST)
            width = dp2px(200f).toInt()

        if(heightMode == MeasureSpec.AT_MOST)
            height = dp2px(200f).toInt()

        width = if(width >= height) height else width //取最短的作为宽也就是圆弧的直径

        var radius = width.toDouble()/2 // 这个圆弧的半径

        // 圆弧的实际宽高
//        setMeasuredDimension(width, width/2+sqrt(radius*radius/2).toInt())

        //宽高是整个圆的宽高
        setMeasuredDimension(width, width)
    }


    override fun onDraw(canvas: Canvas) {
        /**
         * left :mBorderWidth/2 ,mBorderWidth的一般开始画,就可以贴边
         * top :mBorderWidth/2 ,mBorderWidth的一般开始画,就可以贴边
         * right :width-mBorderWidth/2 ,画到宽度减去画笔宽度的一般,这样右边画笔也可以贴边
         */
        val rect = RectF(mBorderWidth/2,mBorderWidth/2,width-mBorderWidth/2,width-mBorderWidth/2)

        if(mCurrentStep == 0)return

        //画底部圆弧
        /**
         * rect : 圆弧的边界
         * 135f : 开始的角度,水平右边的角度为0f
         * 270f : 画笔画圆弧时扫过的角度.
         * false : 圆中心到圆弧起始/末位点的线不画
         * mOuterPaint : 画笔
         */
        canvas.drawArc(rect,135f,270f,false,mOuterPaint)

        //画顶部圆弧
        canvas.drawArc(rect,135f,(mCurrentStep.toFloat()/mStepMax)*270f,false,mInnerPaint)

        //画中心文字
        val stepProgress = mCurrentStep.toString()
        var textBounds = Rect()
        mTextPaint.getTextBounds(stepProgress,0, stepProgress.length,textBounds)
        val fontMetricsInt = mTextPaint.fontMetricsInt
        var dy = (fontMetricsInt.descent - fontMetricsInt.ascent)/2 - fontMetricsInt.descent
        canvas.drawText(stepProgress, (width/2-textBounds.width()/2).toFloat(),
            (width/2+dy).toFloat(),mTextPaint)
    }

    @Synchronized
    fun setCurrentStep(currentStep : Int){
        mCurrentStep = currentStep

        /**
         * 会重新调用onDraw()方法
         * 这个控件的所有父布局的OnDraw方法都会调用一遍
         * 所以注意过度渲染问题
         */
        invalidate()
    }

    @Synchronized
    fun setStepMax(max:Int){
        this.mStepMax = max
    }


    fun sp2px(sp:Float) : Float{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,resources.displayMetrics)
    }

    fun dp2px(dp:Float) : Float{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,resources.displayMetrics)
    }
}