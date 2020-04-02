package com.east.architect_zenghui.architect_36_retrofit_optimize.simple6;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by hcDarren on 2017/12/16.
 * 请求后台访问数据的 接口类
 */
public interface ServiceApi {
    // 两个参数，一个是 url,一个是 params
    @POST()
    Call<ResponseBody> postMethod(@Url String url, @FieldMap Map<String, Object> params);

    @GET()
    Call<ResponseBody> getMethod(@Url String url, @QueryMap Map<String, Object> params);
}
