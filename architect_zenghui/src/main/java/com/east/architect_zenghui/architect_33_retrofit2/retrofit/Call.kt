package com.east.architect_zenghui.architect_33_retrofit2.retrofit


/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:
 *  @author: jamin
 *  @date: 2020/3/30
 * |---------------------------------------------------------------------------------------------------------------|
 */
interface Call<T> {
    fun enqueue(callback:Callback<T>)
}