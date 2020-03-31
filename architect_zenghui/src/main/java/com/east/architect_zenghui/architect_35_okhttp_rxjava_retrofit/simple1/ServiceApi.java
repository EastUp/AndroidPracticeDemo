package com.east.architect_zenghui.architect_35_okhttp_rxjava_retrofit.simple1;


import com.east.architect_zenghui.architect_35_okhttp_rxjava_retrofit.simple2.Result;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by hcDarren on 2017/12/16.
 * 请求后台访问数据的 接口类
 */
public interface ServiceApi {
    // 接口涉及到解耦，userLogin 方法是没有任何实现代码的
    // 如果有一天要换 GoogleHttp

    @POST("login")// 登录接口 GET(相对路径)
    @FormUrlEncoded
    Observable<Result<UserInfo>> userLogin(
            // @Query(后台需要解析的字段)
            @Field("account") String userName,
            @Field("password") String userPwd);

    // POST

    @POST("login")// 登录接口 GET(相对路径)
    @FormUrlEncoded
    Call<Result<UserInfo>> userLogin1(
            // @Query(后台需要解析的字段)
            @Field("account") String userName,
            @Field("password") String userPwd);
    // 上传文件怎么用？
}
