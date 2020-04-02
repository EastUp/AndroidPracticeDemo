package com.east.architect_zenghui.architect_36_retrofit_optimize.simple6;

import android.content.Context;

import com.east.architect_zenghui.architect_36_retrofit_optimize.simple5.IHttpRequest;
import com.east.architect_zenghui.architect_36_retrofit_optimize.simple7.EngineCallback;

import java.io.IOException;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hcDarren on 2017/12/24.
 */
public class RetrofitRequest implements IHttpRequest {
    // Retrofit 兼容进来，
    @Override
    public void   get(Context context, String url, Map<String, Object> params, final EngineCallback callback, boolean cache) {
        // 1. 第一个要解决的问题就是 url 是作为了一个参数，但是 Retrofit 是注解
        // 2. 返回值应该怎么处理？不应该直接指定泛型对象，只能通用
        // 解决思路：1. POST() 和 GET 注解不能用 url 作为注解参数，用 @Url 代替他
        RetrofitClient.getServiceApi().getMethod(url,params).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // 成功解析即可
                ResponseBody body = response.body();
                if(body == null){
                    // 401
                    body = response.errorBody();
                }
                try {
                    callback.onSuccess(body.string());
                } catch (IOException e) {
                    callback.onFailure(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailure(new RetrofitErrorException(t.getMessage()));
            }
        });
    }

    @Override
    public void post(Context context, String url, Map<String, Object> params, final EngineCallback callback, boolean cache) {
        // 1. 第一个要解决的问题就是 url 是作为了一个参数，但是 Retrofit 是注解
        // 2. 返回值应该怎么处理？不应该直接指定泛型对象，只能通用

        // 解决思路：1. POST() 和 GET 注解不能用 url 作为注解参数，用 @Url 代替他
        RetrofitClient.getServiceApi().postMethod(url,params).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // 成功解析即可
                ResponseBody body = response.body();
                if(body == null){
                    // 401
                    body = response.errorBody();
                }
                try {
                    callback.onSuccess(body.string());
                } catch (IOException e) {
                    callback.onFailure(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailure(new RetrofitErrorException(t.getMessage()));
            }
        });
    }

    @Override
    public void download(Context context, String url, Map<String, Object> params, final EngineCallback callback) {

    }

    @Override
    public  void upload(Context context, String url, Map<String, Object> params,final EngineCallback callback) {

    }
}
