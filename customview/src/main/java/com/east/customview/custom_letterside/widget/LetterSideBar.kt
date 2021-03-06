package com.east.customview.custom_letterside.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import com.east.customview.R
import com.east.customview.custom_letterside.LetterSideActivity

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 字母索引
 *  @author: East
 *  @date: 2019-11-14
 * |---------------------------------------------------------------------------------------------------------------|
 */
class LetterSideBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // 定义26个字母
    var mLetters = arrayOf(
        "A",
        "B",
        "C",
        "D",
        "E",
        "F",
        "G",
        "H",
        "I",
        "J",
        "K",
        "L",
        "M",
        "N",
        "O",
        "P",
        "Q",
        "R",
        "S",
        "T",
        "U",
        "V",
        "W",
        "X",
        "Y",
        "Z",
        "#"
    )
    var mLetterTouchListener: LetterTouchListener? = null
    private var mTextSize: Int
    private var mTextColor: ColorStateList
    private var mHighlightTextColor: ColorStateList
    private var mPaint: Paint
    //当前触摸的字符
    private var mCurrentSeletedLetter: String? = null

    init {
        var typedArray = context.obtainStyledAttributes(attrs, R.styleable.LetterSideBar)
        mTextSize = typedArray.getDimensionPixelSize(
            R.styleable.LetterSideBar_letterTextSize,
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                12f,
                resources.displayMetrics
            ).toInt()
        )
        mTextColor = typedArray.getColorStateList(R.styleable.LetterSideBar_letterTextColor)
            ?: ColorStateList(
                arrayOf(drawableState), intArrayOf(Color.BLUE)
            )

        mHighlightTextColor =
            typedArray.getColorStateList(R.styleable.LetterSideBar_letterHignlightTextColor)
                ?: ColorStateList(
                    arrayOf(drawableState), intArrayOf(Color.RED)
                )
        typedArray.recycle()

        mPaint = Paint()
        mPaint.color = mTextColor.getColorForState(drawableState, Color.BLUE)
        mPaint.textSize = mTextSize.toFloat()
        mPaint.isAntiAlias = true //抗锯齿
        mPaint.isDither = true //防抖动
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 计算指定宽度 = 左右的padding + 字母的宽度(取决于你的画笔)
        val textWidth = mPaint.measureText("A") //A字母的宽度
        val width = textWidth + paddingLeft + paddingRight
        //// 高度可以直接获取
        val height = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(width.toInt(), height)
    }


    override fun onDraw(canvas: Canvas) {
        var textHeight = (measuredHeight - paddingTop - paddingBottom) / mLetters.size
        for ((i, letter) in mLetters.withIndex()) {
            // x 绘制在最中间 = 宽度/2 - 文字/2
            var x = (measuredWidth - mPaint.measureText(letter)) / 2
            // 知道每个字母的中心位置  1  字母的高度一半   2 字母高度一般+前面字符的高度
            var letterCenterY = paddingTop + i * textHeight + textHeight / 2
            // 基线，基于中心位置
            val fontMetricsInt = mPaint.fontMetricsInt
            var dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom
            var baseline = letterCenterY + dy
            if (mCurrentSeletedLetter == letter)
                mPaint.color = mHighlightTextColor.getColorForState(drawableState, Color.RED)
            else
                mPaint.color = mTextColor.getColorForState(drawableState, Color.BLUE)
            canvas.drawText(
                letter,
                x,
                baseline.toFloat(),
                mPaint
            )
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var textHeight = (measuredHeight - paddingTop - paddingBottom) / mLetters.size
        when (event.action) {
            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_MOVE -> {
                val y = event.y
                var position = ((y - paddingTop) / textHeight).toInt()
                if (position < 0)
                    position = 0
                else if (position > mLetters.size - 1)
                    position = mLetters.size - 1
                var selectLetter= mLetters[position]
                mLetterTouchListener?.onTouch(selectLetter,true)
                if(mCurrentSeletedLetter!=selectLetter){
                    mCurrentSeletedLetter = selectLetter
                    invalidate()
                }
            }
            MotionEvent.ACTION_UP -> {
                mLetterTouchListener?.onTouch(mCurrentSeletedLetter!!, false)
            }
        }
        return true   //把事件传递下去
    }


    /**
     *  接口回调
     */
    interface LetterTouchListener {
        /**
         * @param letter :当前选中的字母
         * @param up : 手指是否松开
         */
        fun onTouch(letter: String, isTouch: Boolean)
    }

}