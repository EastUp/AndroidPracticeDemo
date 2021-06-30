package com.east.framelibrary.http;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.east.baselibrary.http.EngineCallBack;
import com.east.baselibrary.http.IHttpEngine;
import com.east.baselibrary.http.Utils;
import com.east.baselibrary.utils.MD5Util;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description:  OkHttp的引擎
 *  @author: jamin
 *  @date: 2020/5/18
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class OkHttpEngine implements IHttpEngine {
    private static OkHttpClient mOkHttpClient = new OkHttpClient();

    @Override
    public void get(final boolean cache, Context context, String url, Map<String, Object> params, final EngineCallBack callback) {
        url = Utils.jointParams(url, params);

        String resultJson = null; //缓存中的结果
        if (cache) {  //如果有缓存，就去数据库中读取
            resultJson = CacheDataUtil.getCacheResultJson(url);
            if (!TextUtils.isEmpty(resultJson))
                callback.onSuccess(resultJson);
        }

        Request.Builder builder = new Request.Builder().url(url).tag(context);
        Request request = builder.build();

        final String finalResultJson = resultJson;
        final String finalUrl = url;
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.onError(e);
                CacheDataUtil.cacheData(new CacheData(MD5Util.string2MD5(finalUrl),"result"));//不管如何都先缓存进数据库
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result = response.body().string();

                if (cache) {
                    if (!TextUtils.isEmpty(finalResultJson) && TextUtils.equals(finalResultJson,result)){
                        //如果相同的话没必要再刷新界面了
                        Log.d("TAG","数据缓存一致"+result);
                        return;
                    }else{
                        CacheDataUtil.cacheData(new CacheData(MD5Util.string2MD5(finalUrl),result));//不管如何都先缓存进数据库
                    }
                }

                callback.onSuccess(result);
            }
        });
    }

    @Override
    public void post(final boolean cache, Context context, final String url, Map<String, Object> params, final EngineCallBack callback) {

        String resultJson = null; //缓存中的结果
        if (cache) {  //如果有缓存，就去数据库中读取
            resultJson = CacheDataUtil.getCacheResultJson(url);
            if (!TextUtils.isEmpty(resultJson))
                callback.onSuccess(resultJson);
        }

        RequestBody requestBody = appendBody(params);

        Request request = new Request.Builder()
                .url(url)
                .tag(context)
                .post(requestBody)
                .build();

        final String finalResultJson = resultJson;
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.onError(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String result = response.body().string();

                if (cache) {
                    if (!TextUtils.isEmpty(finalResultJson) && TextUtils.equals(finalResultJson,result)){
                        //如果相同的话没必要再刷新界面了
                        Log.d("TAG","数据缓存一致"+result);
                        return;
                    }
                }

                callback.onSuccess(result);
                CacheDataUtil.cacheData(new CacheData(url,result));//不管如何都先缓存进数据库
            }
        });

    }

    /**
     * 组装post请求参数body
     */
    private RequestBody appendBody(Map<String, Object> params) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        addParams(builder, params);
        return builder.build();
    }

    /**
     * 添加参数
     */
    private void addParams(MultipartBody.Builder builder, Map<String, Object> params) {
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                Object value = params.get(key);
                builder.addFormDataPart(key, value + "");
                if (value instanceof File) {
                    //处理文件
                    File file = (File) value;
                    builder.addFormDataPart(key, file.getName()
                            , RequestBody.create(
                                    MediaType.parse(guessMimeType(file.getAbsolutePath()))
                                    , file)
                    );
                }
            }
        }
    }

    private String guessMimeType(String absolutePath) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(absolutePath);
        if (contentTypeFor == null)
            contentTypeFor = "application/octet-stream";
        return contentTypeFor;
    }
}
