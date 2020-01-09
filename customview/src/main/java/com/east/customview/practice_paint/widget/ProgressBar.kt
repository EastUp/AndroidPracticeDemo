package com.east.customview.practice_paint.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.east.customview.R

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 炫酷进度条
 *  @author: East
 *  @date: 2019-11-13
 * |---------------------------------------------------------------------------------------------------------------|
 */
class ProgressBar @JvmOverloads constructor(
    context:Context,
    attrs : AttributeSet?=null,
    defStyleAttr : Int = 0
):View(context,attrs,defStyleAttr){
    private var mInnerColor : ColorStateList //内圆颜色
    private var mOuterColor : ColorStateList //外圆颜色
    private var mBorderWidth : Float = dip2px(10f)//圆的宽度
    private var mTextSize : Float = sp2px(20f)//文字大小
    private var mTextColor :ColorStateList //中心文字颜色
    private var mMax :Float = 100f//总进度
    private var mProgress :Float = 50f//当前进度

    private var mCirclePaint:Paint
    private var mTextPaint:Paint
    init {
        var typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressBar)
        mInnerColor = typedArray.getColorStateList(R.styleable.ProgressBar_innerCircleColor)?: ColorStateList(
            arrayOf(drawableState), intArrayOf(Color.BLUE)
        )
        mOuterColor = typedArray.getColorStateList(R.styleable.ProgressBar_outerCircleColor)?: ColorStateList(
            arrayOf(drawableState), intArrayOf(Color.RED)
        )
        mBorderWidth = typedArray.getDimension(R.styleable.ProgressBar_progressBarBorderWidth,mBorderWidth)
        mTextSize =
            typedArray.getDimensionPixelSize(R.styleable.ProgressBar_progressBarTextSize,mTextSize.toInt())
                .toFloat()
        mTextColor = typedArray.getColorStateList(R.styleable.ProgressBar_progressBarTextColor)?: ColorStateList(
            arrayOf(drawableState), intArrayOf(Color.WHITE)
        )
        mMax = typedArray.getFloat(R.styleable.ProgressBar_progressBarMax,mMax)
        mProgress = typedArray.getFloat(R.styleable.ProgressBar_progressBarProgress,mProgress)

        typedArray.recycle()

        mCirclePaint = Paint()
        mCirclePaint.color = mInnerColor.getColorForState(drawableState,Color.BLUE)
        mCirclePaint.isAntiAlias = true //抗锯齿
        mCirclePaint.isDither = true //仿抖动
        mCirclePaint.strokeWidth = mBorderWidth
        mCirclePaint.style = Paint.Style.STROKE

        mTextPaint = Paint()
        mTextPaint.isAntiAlias = true
        mTextPaint.textSize = mTextSize
        mTextPaint.color = mTextColor.getColorForState(drawableState,Color.WHITE)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        var width = MeasureSpec.getSize(widthMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)

        if(widthMode == MeasureSpec.AT_MOST){ //证明是wrap_content
            width = dip2px(200f).toInt()
        }

        if(heightMode == MeasureSpec.AT_MOST){ //证明是wrap_content
            height = dip2px(200f).toInt()
        }

        setMeasuredDimension(width.coerceAtMost(height), width.coerceAtMost(height))
    }

    override fun onDraw(canvas: Canvas) {
        mCirclePaint.color = mInnerColor.getColorForState(drawableState,Color.BLUE)
        var center = measuredWidth / 2
        //画内圆
        canvas.drawCircle(center.toFloat(), center.toFloat(),center-mBorderWidth/2,mCirclePaint)

        if(mMax < 0){
            mMax = 100f
        }

        if(mProgress < 0f)
            mProgress = 0f
        else if(mProgress > mMax)
            mProgress = mMax
        mCirclePaint.color = mOuterColor.getColorForState(drawableState,Color.RED)
        var rect = RectF(mBorderWidth/2,mBorderWidth/2,measuredWidth-mBorderWidth/2,measuredHeight-mBorderWidth/2)
        //画外圆
        canvas.drawArc(rect, 0F,mProgress/mMax*360,false,mCirclePaint)

        var text = "${(mProgress/mMax * 100).toInt()}%"
        var bounds = Rect()
        mTextPaint.getTextBounds(text,0,text.length,bounds)
        var x = center - bounds.width()/2
        val fontMetricsInt = mTextPaint.fontMetricsInt
        var dy = (fontMetricsInt.descent - fontMetricsInt.ascent)/2 - fontMetricsInt.descent
        canvas.drawText(text, x.toFloat(), (center+dy).toFloat(),mTextPaint)
    }

    @Synchronized
    fun setProgress(progress:Float){
        this.mProgress = progress
        invalidate()
    }

    @Synchronized
    fun setMax(max:Float){
        this.mMax = max
        invalidate()
    }


    fun sp2px(sp:Float):Float{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,resources.displayMetrics)
    }

    fun dip2px(dip:Float):Float{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dip,resources.displayMetrics)
    }
}