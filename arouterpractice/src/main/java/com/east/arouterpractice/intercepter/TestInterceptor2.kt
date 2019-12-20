package com.east.arouterpractice.intercepter

import android.content.Context
import android.util.Log
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:
 *  @author: East
 *  @date: 2019-07-18
 * |---------------------------------------------------------------------------------------------------------------|
 */
@Interceptor(priority = 5,name = "测试拦截器")
class TestInterceptor2 : IInterceptor{

    override fun process(postcard: Postcard?, callback: InterceptorCallback?) {
        if(postcard?.path == "/arouter1/activity4"){
            Log.i("TestInterceptor拦截器","TestInterceptor2")
            callback?.onContinue(postcard)
        }else{
            callback?.onContinue(postcard)
        }
    }

    override fun init(context: Context?) {

    }
}