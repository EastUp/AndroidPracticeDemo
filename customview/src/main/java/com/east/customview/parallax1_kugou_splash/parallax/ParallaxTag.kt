package com.east.customview.parallax1_kugou_splash.parallax

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 视差动画属性值
 *  @author: East
 *  @date: 2020-01-09
 * |---------------------------------------------------------------------------------------------------------------|
 */
data class ParallaxTag(var translationXIn:Float = 0f,
                       var translationXOut:Float = 0f,
                       var translationYIn:Float = 0f,
                       var translationYOut:Float = 0f) {

    override fun toString() =
        ("translationXIn->" + translationXIn + " translationXOut->" + translationXOut
                + " translationYIn->" + translationYIn + " translationYOut->" + translationYOut)
}