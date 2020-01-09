package com.east.customview.besier3_huajiao_praise

import android.animation.TypeEvaluator
import android.graphics.PointF
import kotlin.math.pow

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:自定义路径属性动画估值器
 *  @author: East
 *  @date: 2020-01-08
 * |---------------------------------------------------------------------------------------------------------------|
 */
class LoveTypeEvaluator(var point1:PointF,var point2:PointF) :TypeEvaluator<PointF>{

    override fun evaluate(fraction: Float, point0: PointF, point3: PointF): PointF {

        // fraction 是 [0,1]  开始套公式 公式有四个点 还有两个点从哪里来（构造函数中来）
        var pointF = PointF()

        pointF.x = point0.x* (1 - fraction).pow(3) +
                3*point1.x*fraction*(1 - fraction).pow(2)+
                3*point2.x*fraction.pow(2)*(1-fraction)+
                point3.x*fraction.pow(3)
        pointF.y = point0.y* (1 - fraction).pow(3) +
                3*point1.y*fraction*(1 - fraction).pow(2)+
                3*point2.y*fraction.pow(2)*(1-fraction)+
                point3.y*fraction.pow(3)
        return pointF
    }
}