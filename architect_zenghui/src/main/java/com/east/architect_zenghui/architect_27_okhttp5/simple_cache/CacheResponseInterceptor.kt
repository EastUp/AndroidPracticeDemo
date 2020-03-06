package com.east.architect_zenghui.architect_27_okhttp5.simple_cache

import okhttp3.Interceptor
import okhttp3.Response

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 有网 30s 内请求读缓存
 *  @author: East
 *  @date: 2020/3/6
 * |---------------------------------------------------------------------------------------------------------------|
 */
class CacheResponseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var response = chain.proceed(chain.request())
        // 过期时间是 30s
        response = response.newBuilder()
            .removeHeader("Cache-Control")
            .removeHeader("Pragma")
            .addHeader("Cache-Control","max-age=30")
            .build()
        return response
    }
}