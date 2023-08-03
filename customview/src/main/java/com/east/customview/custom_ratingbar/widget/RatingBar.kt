package com.east.customview.custom_ratingbar.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.east.customview.R

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 仿淘宝评价 RatingBar
 *  @author: East
 *  @date: 2019-11-13
 * |---------------------------------------------------------------------------------------------------------------|
 */
class RatingBar @JvmOverloads constructor(
    context: Context,
    attrs:AttributeSet ?= null,
    defStyleAttr : Int = 0
) :View(context,attrs,defStyleAttr) {

    private var mStarNormal : Drawable
    private var mStarFocus : Drawable
    private var mStarNumber : Int
    private var mPadding : Float

    private var mSelectStarNumber = 0//选中的星星
    init {
        var typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatingBar)
        mStarNormal = typedArray.getDrawable(R.styleable.RatingBar_starNormal)?:ContextCompat.getDrawable(context,R.drawable.star_normal)!!
        mStarFocus = typedArray.getDrawable(R.styleable.RatingBar_starFocus)?:ContextCompat.getDrawable(context,R.drawable.star_selected)!!
        mStarNumber = typedArray.getInt(R.styleable.RatingBar_starNumber,5)
        mPadding = typedArray.getDimension(R.styleable.RatingBar_ratingBarPadding,dip2px(10f))
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = mStarNormal.intrinsicWidth*mStarNumber+mPadding*(mStarNumber+1)
        setMeasuredDimension(width.toInt(),mStarNormal.intrinsicHeight)
    }

    override fun onDraw(canvas: Canvas) {
        for(i in 0 until mStarNumber){
            val left = (i + 1) * mPadding + i * mStarNormal.intrinsicWidth
            mStarNormal.bounds = Rect(left.toInt(),0,
                (left + mStarNormal.intrinsicWidth).toInt(),mStarNormal.intrinsicHeight)
            mStarNormal.draw(canvas)
        }

        for(i in 0 until mSelectStarNumber){
            val left = (i + 1) * mPadding + i * mStarNormal.intrinsicWidth
            mStarFocus.bounds = Rect(left.toInt(),0,
                (left + mStarNormal.intrinsicWidth).toInt(),mStarNormal.intrinsicHeight)
            mStarFocus.draw(canvas)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action){
            MotionEvent.ACTION_DOWN,MotionEvent.ACTION_MOVE -> {
                val x = event.x  //相对于父View的水平距离 rawX是相对于屏幕的水平距离
                val fl = mPadding + mStarNormal.intrinsicWidth
                var currentStarNumber = (x / fl).toInt()
                if(x - fl*currentStarNumber > mPadding)
                    currentStarNumber += 1

                if(currentStarNumber!=mSelectStarNumber){  //减少invalidate的调用,优化UI
                    mSelectStarNumber = currentStarNumber
                    invalidate()
                }
            }
        }

        return true //将事件继续分发下去
    }



    fun dip2px(dip:Float):Float{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dip,resources.displayMetrics)
    }
}