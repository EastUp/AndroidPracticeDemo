package com.east.architect_zenghui.architect_33_retrofit1.simple2

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: Retrofit的工具类
 *  @author: jamin
 *  @date: 2020/3/16
 * |---------------------------------------------------------------------------------------------------------------|
 */
object RetrofitClient {

    lateinit var mServiceApi : ServiceApi
    init {
        var okhttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Log.e("TAG", message)
                }
            }).apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
        // 各种套路和招式 ，发现问题解决问题，基础，源码的理解
        // 1. 没打印？
        // 2. 数据格式不一致？成功 data 是个对象，不成功 data 是个 String
        // 3. 还有就是 baseUrl 问题？ (Retrofit 找不到任何入口可以修改)
        //        3.1 不同的 baseUrl 构建不同的 Retrofit 对象 （直不应该首选）
        //        3.2 自己想办法，取巧也行走漏洞

        var retrofit = Retrofit.Builder()
            // 访问后台接口的主路径
            .baseUrl("http://192.168.6.143:8080/OkHttpServer/")
            // 添加解析转换工厂,Gson 解析，Xml解析，等等
            .addConverterFactory(GsonConverterFactory.create())
            // 添加 OkHttpClient,不添加默认就是 光杆 OkHttpClient
            .client(okhttpClient)
            .build()

        //创建一个实例对象
        mServiceApi = retrofit.create(ServiceApi::class.java)
    }

    
    fun getServiceApi(): ServiceApi = mServiceApi
}