package com.east.architect_zenghui.architect_33_retrofit2.retrofit

import com.east.architect_zenghui.architect_33_retrofit2.simple.UserInfo


/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:
 *  @author: jamin
 *  @date: 2020/3/30
 * |---------------------------------------------------------------------------------------------------------------|
 */
interface Call<T> {
    fun enqueue(callback: Callback<T>)
}