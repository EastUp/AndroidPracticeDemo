package com.east.architect_zenghui.architect2_aop.aop_singleclick

import android.os.SystemClock
import kotlin.math.abs

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:
 *  @author: East
 *  @date: 2020-02-05
 * |---------------------------------------------------------------------------------------------------------------|
 */
object FastClickUtils {
    /**
     * 最后一次点击的时间
     */
    private var mLastClickTime = 0L

    /**
     *  检测是否快速点击
     *  @param intervalMillis 两次点击间隔时间
     *  @return true 是,false 不是
     */
    fun checkFastClick(intervalMillis:Long):Boolean{
        //使用：System.currentTimeMillis()用于和日期相关的地方，比如日志。SystemClock.elapsedRealtime()用于某个事件经历的时间，比如两次点击的时间间隔。
        var time = SystemClock.elapsedRealtime()
        var timeInterval = abs(time - mLastClickTime)
        return if(timeInterval < intervalMillis)
            true
        else{
            mLastClickTime = time
            false
        }
    }

}