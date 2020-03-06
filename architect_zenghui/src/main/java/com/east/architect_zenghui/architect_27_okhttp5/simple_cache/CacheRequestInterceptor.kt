package com.east.architect_zenghui.architect_27_okhttp5.simple_cache

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: 无网直接读缓存
 *  @author: East
 *  @date: 2020/3/6
 * |---------------------------------------------------------------------------------------------------------------|
 */
class CacheRequestInterceptor(private var mContext: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (isNetWork(mContext)) {
            request = request.newBuilder()
                .cacheControl(CacheControl.FORCE_CACHE)
                .build()
        }
        return chain.proceed(request)
    }

    private fun isNetWork(context: Context): Boolean {
        var cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = cm.activeNetworkInfo
        return activeNetworkInfo?.isAvailable ?: false
    }
}