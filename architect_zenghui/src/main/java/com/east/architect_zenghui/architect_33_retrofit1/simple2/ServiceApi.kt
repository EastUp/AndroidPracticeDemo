package com.east.architect_zenghui.architect_33_retrofit1.simple2

import com.east.architect_zenghui.architect_34_retrofit2.simple.Result
import com.east.architect_zenghui.architect_34_retrofit2.simple.UserInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *  @description: Retrofit测试接口
 *  @author: jamin
 *  @date: 2020/3/16
 * |---------------------------------------------------------------------------------------------------------------|
 */
interface ServiceApi {
    // 接口涉及到解耦，userLogin 方法是没有任何实现代码的
    // 如果有一天要换 GoogleHttp

    @GET("LoginServlet") // 登录接口 GET(相对路径)
    fun userlogin(
        // @Query(后台需要解析的字段)
        @Query("userName")userName:String
        ,@Query("password")userPwd:String)
            : Call<Result<UserInfo>>

    // POST

    // 上传文件怎么用？
}