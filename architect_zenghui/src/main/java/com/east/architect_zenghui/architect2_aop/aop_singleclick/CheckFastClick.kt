package com.east.architect_zenghui.architect2_aop.aop_singleclick

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 检测按钮是否快速点击,默认是1000
 *  @author: East
 *  @date: 2020-02-05
 * |---------------------------------------------------------------------------------------------------------------|
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class CheckFastClick (val value : Long = 1000)