package com.east.customview.custom_tablayout.widget

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import com.east.customview.custom_tablayout.BaseAdapter

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 流式布局,帮助学些ViewGroup知识
 *  @author: East
 *  @date: 2019-11-20
 * |---------------------------------------------------------------------------------------------------------------|
 */
class TabLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs,defStyleAttr) {


    lateinit var mAdapter:BaseAdapter

    /**
     *  OnMeasure会调用两次
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = MeasureSpec.getSize(widthMeasureSpec)
        var height = 0 //总高度

        var lineWidth = 0 //每行的宽度

        var maxHeight = 0 //每行的最高高度

        for (i in 0 until childCount){
            val child = getChildAt(i)

            //测量child 这个方法调用后就可以拿到child的宽高了
            measureChild(child,widthMeasureSpec,heightMeasureSpec)

            //如果长度超过了宽度就换一行
            if(lineWidth+child.measuredWidth+child.marginLeft+child.marginRight > width){
                height += maxHeight
                lineWidth = child.measuredWidth+child.marginLeft+child.marginRight //宽度也直接复制为第一个child的宽度
                maxHeight = child.measuredHeight + child.marginTop + child.marginBottom //maxHeight重新赋值为第一个child的高度
            }else{
                lineWidth += child.measuredWidth+child.marginLeft+child.marginRight
                maxHeight =
                    maxHeight.coerceAtLeast(child.measuredHeight + child.marginTop + child.marginBottom)

            }
        }

        //把最后一行的高度加上
        height += maxHeight

        setMeasuredDimension(width,height)

    }

    /**
     *  返回MarginLayoutParams 才能拿到child的margin值
     */
    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context,attrs)
    }


    /**
     *  摆放child
     */
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var left = 0
        var top = 0

        var maxHeight = 0 //每行的最高高度

        for (i in 0 until childCount){
            val child = getChildAt(i)
            //如果长度超过了宽度就换一行
            if(left+child.measuredWidth+child.marginLeft+child.marginRight > width){
                top += maxHeight
                left = 0
                child.layout(left+child.marginLeft,top+child.marginTop,left+child.marginLeft+child.measuredWidth,top+child.marginTop+child.measuredHeight)
                left += child.measuredWidth+child.marginLeft+child.marginRight //宽度也直接复制为第一个child的宽度
                maxHeight = child.measuredHeight + child.marginTop + child.marginBottom //maxHeight重新赋值为第一个child的高度
            }else{
                child.layout(left+child.marginLeft,top+child.marginTop,left+child.marginLeft+child.measuredWidth,top+child.marginTop+child.measuredHeight)
                left += child.measuredWidth+child.marginLeft+child.marginRight
                maxHeight =
                    maxHeight.coerceAtLeast(child.measuredHeight + child.marginTop + child.marginBottom)

            }
        }

    }


    /**
     *  设置Adapter
     */
    fun setAdapter(adapter :BaseAdapter?){
        if(adapter == null){
            throw RuntimeException("Adapter不能为空")
        }
        mAdapter = adapter

        removeAllViews()

        for(i in 0 until  mAdapter.getCount()){
            val child = mAdapter.getView(i, this)
            addView(child)
        }

    }

}