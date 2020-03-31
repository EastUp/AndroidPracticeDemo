package com.east.architect_zenghui.architect_34_retrofit2.retrofit

import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Request

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description:  添加参数之类的
 *  @author: jamin
 *  @date: 2020/3/30 17:24
 * |---------------------------------------------------------------------------------------------------------------|
 */
class RequestBuilder(
    baseUrl: String,
    relativeUrl: String?,
    var httpMethod: String?,
    var parameterHandlers: Array<ParameterHandler?>,
    var args: Array<out Any>
) {
    var httpUrl = (baseUrl+relativeUrl).toHttpUrlOrNull()!!.newBuilder()

    fun build(): Request {
        for((i,value) in args.withIndex()){
            if(parameterHandlers[i] == null)
            // userName = Darren
            parameterHandlers[i]!!.apply(this,value)
        }
        //POST 等等
        return Request.Builder().url(httpUrl.build()).build()
    }

    fun addQueryName(key: String, value: String) {
        // userName = Darren&password = 940223
        if(httpMethod == "GET")
            httpUrl.addQueryParameter(key,value)
    }

}
