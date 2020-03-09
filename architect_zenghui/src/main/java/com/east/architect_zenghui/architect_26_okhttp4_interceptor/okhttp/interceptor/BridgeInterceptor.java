package com.east.architect_zenghui.architect_26_okhttp4_interceptor.okhttp.interceptor;

import android.util.Log;

import com.east.architect_zenghui.architect_26_okhttp4_interceptor.okhttp.Request;
import com.east.architect_zenghui.architect_26_okhttp4_interceptor.okhttp.RequestBody;
import com.east.architect_zenghui.architect_26_okhttp4_interceptor.okhttp.Response;

import java.io.IOException;

/**
 * Created by hcDarren on 2017/11/19.
 */

public class BridgeInterceptor implements Interceptor{

    @Override
    public Response intercept(Chain chain) throws IOException {
        Log.e("TAG","BridgeInterceptor");
        Request request = chain.request();
        // 添加一些请求头
        request.header("Connection","keep-alive");
        // 做一些其他处理
        if(request.requestBody()!=null){
            RequestBody requestBody = request.requestBody();
            request.header("Content-Type",requestBody.getContentType());
            request.header("Content-Length", Long.toString(requestBody.getContentLength()));
        }
        Response response = chain.proceed(request);

        return response;
    }
}
