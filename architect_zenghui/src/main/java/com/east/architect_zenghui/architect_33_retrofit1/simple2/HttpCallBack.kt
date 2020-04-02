package com.east.architect_zenghui.architect_33_retrofit1.simple2

import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.ParameterizedType

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: retrofit回调封装
 *  @author: jamin
 *  @date: 2020/3/16
 * |---------------------------------------------------------------------------------------------------------------|
 */
abstract class HttpCallBack<T>:Callback<Result<T>> {
    override fun onFailure(call: Call<Result<T>>, t: Throwable) {
        // 处理失败，联网，解析出错，自己弄一弄
        onError("400",t.message.toString())
    }

    override fun onResponse(call: Call<Result<T>>, response: Response<Result<T>>) {
        var result = response.body()
        if(!result!!.isSuccessed()){
            onError(result.code!!, result.msg!!)
            return
        }

        // 解析,获取类上面的泛型
        var clazz = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
        val data = Gson().fromJson(result.data.toString(), clazz)
        onSuccessed(data)
    }

    abstract fun onSuccessed(result:T)

    abstract fun onError(code:String,msg:String)
}