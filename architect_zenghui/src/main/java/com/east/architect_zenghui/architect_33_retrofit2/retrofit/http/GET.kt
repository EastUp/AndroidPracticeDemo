package com.east.architect_zenghui.architect_33_retrofit2.retrofit.http

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:
 *  @author: jamin
 *  @date: 2020/3/30
 * |---------------------------------------------------------------------------------------------------------------|
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class GET(val value:String)