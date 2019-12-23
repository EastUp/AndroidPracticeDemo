package com.east.customview.custom_behavior

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:
 *  @author: East
 *  @date: 2019-12-23
 * |---------------------------------------------------------------------------------------------------------------|
 */
class TranslationBehavior @JvmOverloads constructor(
    context: Context ?= null,
    attrs : AttributeSet ?= null
) : CoordinatorLayout.Behavior<View>(context,attrs) {

    /**
     *  只运行处理纵向滑动
     */
    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return (axes and ViewCompat.SCROLL_AXIS_VERTICAL)!=0
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        Log.e(
            "TAG",
            "dyConsumed -> $dyConsumed dyUnconsumed -> $dyUnconsumed"
        )

        //向上滑动隐藏
        if(dyConsumed > 0){
            var translationY = (child.layoutParams as ViewGroup.MarginLayoutParams).bottomMargin+child.height
            child.animate().translationY(translationY.toFloat()).setDuration(300).start()
        }else{ //向下滑动显示
            child.animate().translationY(0f).setDuration(300).start()
        }

    }

}