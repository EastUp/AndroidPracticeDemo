package com.east.architect_zenghui.architect_33_retrofit2.retrofit

import android.util.Log
import com.east.architect_zenghui.architect_33_retrofit2.simple.UserInfo
import java.io.IOException


class OkHttpCall<T>(var serviceMethod: ServiceMethod, var args: Array<out Any>) :Call<T>{

    /**
     * 发起请求
     */
    override fun enqueue(callback: Callback<T>) {
        // 发起一个请求，给一个回调就完结了
        Log.e("TAG", "正式发起请求")
        var call :okhttp3.Call = serviceMethod.createNewCall(args)
        call.enqueue(object :okhttp3.Callback{
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                callback.onFailure(this@OkHttpCall,e)
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                // 解析 Response -> Response<T> 回调
                // Log.e("TAG",response.body().string());
                // 涉及到解析，不能在这里写死，ConvertFactory
                var rResponse = Response<T>()
                rResponse.body = serviceMethod.parseBody<T>(response.body)
                callback.onResponse(this@OkHttpCall, rResponse)
            }

        })
    }
}
