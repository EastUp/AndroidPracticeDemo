package com.east.spannableString.utils

import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 富文本编辑，设置一个字符串中的字体、颜色、图片等
 *  @author: East
 *  @date: 2019-06-06
 * |---------------------------------------------------------------------------------------------------------------|
 */
object SpanUtils{
    /**
     * @param span 需要设置的文本
     * @param color 前景色的颜色
     * @param startInclude 起始下标(包括)
     * @param endExclude 结束下标（不包括）
     */
    fun setForegroundColorSpan(span : SpannableString,color:Int,startInclude : Int,endExclude:Int) :SpannableString{
        span.setSpan(ForegroundColorSpan(color),startInclude,endExclude,Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        return span
    }

    /**
     * @param span 需要设置的文本
     * @param color 前景色的颜色
     * @param start 起始下标
     * @param end 结束下标
     * @param flag 下标计算方式
     */
    fun setForegroundColorSpan(span : SpannableString,color:Int,start : Int,end:Int,flag : Int) :SpannableString{
        span.setSpan(ForegroundColorSpan(color),start,end,flag)
        return span
    }

    /**
     * @param span 需要设置的文本
     * @param color 前景色的颜色
     * @param startInclude 起始下标(包括)
     * @param endExclude 结束下标（不包括）
     */
    fun setForegroundColorSpan(span : SpannableStringBuilder,color:Int,startInclude : Int,endExclude:Int) :SpannableStringBuilder{
        span.setSpan(ForegroundColorSpan(color),startInclude,endExclude,Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        return span
    }

    /**
     * @param span 需要设置的文本
     * @param color 前景色的颜色
     * @param start 起始下标
     * @param end 结束下标
     * @param flag 下标计算方式
     */
    fun setForegroundColorSpan(span : SpannableStringBuilder,color:Int,start : Int,end:Int,flag : Int) :SpannableStringBuilder{
        span.setSpan(ForegroundColorSpan(color),start,end,flag)
        return span
    }

    /**                                         上面为设置前景色
     *  |---------------------------------------------------------------------------------------------------------------|
     *                                          下面为设置背景色
     */

    /**
     * @param span 需要设置的文本
     * @param color 前景色的颜色
     * @param startInclude 起始下标(包括)
     * @param endExclude 结束下标（不包括）
     */
    fun setBackgroundColorSpan(span : SpannableString,color:Int,startInclude : Int,endExclude:Int) :SpannableString{
        span.setSpan(BackgroundColorSpan(color),startInclude,endExclude,Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        return span
    }

    /**
     * @param span 需要设置的文本
     * @param color 前景色的颜色
     * @param start 起始下标
     * @param end 结束下标
     * @param flag 下标计算方式
     */
    fun setBackgroundColorSpan(span : SpannableString,color:Int,start : Int,end:Int,flag : Int) :SpannableString{
        span.setSpan(BackgroundColorSpan(color),start,end,flag)
        return span
    }

    /**
     * @param span 需要设置的文本
     * @param color 前景色的颜色
     * @param startInclude 起始下标(包括)
     * @param endExclude 结束下标（不包括）
     */
    fun setBackgroundColorSpan(span : SpannableStringBuilder,color:Int,startInclude : Int,endExclude:Int) :SpannableStringBuilder{
        span.setSpan(BackgroundColorSpan(color),startInclude,endExclude,Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        return span
    }

    /**
     * @param span 需要设置的文本
     * @param color 前景色的颜色
     * @param start 起始下标
     * @param end 结束下标
     * @param flag 下标计算方式
     */
    fun setBackgroundColorSpan(span : SpannableStringBuilder,color:Int,start : Int,end:Int,flag : Int) :SpannableStringBuilder{
        span.setSpan(BackgroundColorSpan(color),start,end,flag)
        return span
    }


    /**
     *  |---------------------------------------------------------------------------------------------------------------|
     *                                          下面为设置图片
     */
    /**
     * @param span 需要设置的文本
     * @param drawable 文本最后追加的图片
     * @param startInclude 起始下标(包括)
     * @param endExclude 结束下标（不包括）
     */
    fun setImageSpanAppend(span : SpannableStringBuilder,drawable:Drawable) :SpannableStringBuilder{
        val beforeLength = span.length
        span.append(" ")
        span.setSpan(ImageSpan(drawable),beforeLength,span.length,Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        return span
    }

    /**
     * @param span 需要设置的文本
     * @param drawable 文本最后追加的图片
     * @param startInclude 起始下标(包括)
     * @param endExclude 结束下标（不包括）
     */
    fun setImageSpan(span : SpannableString,drawable:Drawable,startInclude : Int,endExclude:Int) :SpannableString{
        span.setSpan(ImageSpan(drawable),startInclude,endExclude,Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        return span
    }

    /**
     * @param span 需要设置的文本
     * @param drawable 文本最后追加的图片
     * @param start 起始下标
     * @param end 结束下标
     * @param flag 下标计算方式
     */
    fun setImageSpan(span : SpannableString,drawable:Drawable,start : Int,end:Int,flag : Int) :SpannableString{
        span.setSpan(ImageSpan(drawable),start,end,flag)
        return span
    }

    /**
     * @param span 需要设置的文本
     * @param drawable 文本最后追加的图片
     * @param startInclude 起始下标(包括)
     * @param endExclude 结束下标（不包括）
     */
    fun setImageSpan(span : SpannableStringBuilder,drawable:Drawable,startInclude : Int,endExclude:Int) :SpannableStringBuilder{
        span.setSpan(ImageSpan(drawable),startInclude,endExclude,Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        return span
    }

    /**
     * @param span 需要设置的文本
     * @param drawable 文本最后追加的图片
     * @param start 起始下标
     * @param end 结束下标
     * @param flag 下标计算方式
     */
    fun setImageSpan(span : SpannableStringBuilder,drawable:Drawable,start : Int,end:Int,flag : Int) :SpannableStringBuilder{
        span.setSpan(ImageSpan(drawable),start,end,flag)
        return span
    }


}