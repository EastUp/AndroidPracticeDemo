package com.east.architect_zenghui.architect_26_okhttp4_interceptor.okhttp;

import android.util.Log;

import com.east.architect_zenghui.architect_26_okhttp4_interceptor.okhttp.interceptor.BridgeInterceptor;
import com.east.architect_zenghui.architect_26_okhttp4_interceptor.okhttp.interceptor.CacheInterceptor;
import com.east.architect_zenghui.architect_26_okhttp4_interceptor.okhttp.interceptor.CallServerInterceptor;
import com.east.architect_zenghui.architect_26_okhttp4_interceptor.okhttp.interceptor.Interceptor;
import com.east.architect_zenghui.architect_26_okhttp4_interceptor.okhttp.interceptor.RealInterceptorChain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hcDarren on 2017/11/18.
 */

public class RealCall implements Call{
    private final Request orignalRequest;
    private final OkHttpClient client;
    public RealCall(Request request, OkHttpClient client) {
        orignalRequest = request;
        this.client = client;
    }

    public static Call newCall(Request request, OkHttpClient client) {
        return new RealCall(request,client);
    }

    @Override
    public void enqueue(Callback callback) {
        // 异步的
        AsyncCall asyncCall = new AsyncCall(callback);
        // 交给线程池
        client.dispatcher.enqueue(asyncCall);
    }

    @Override
    public Response execute() {
        return null;
    }

    final class AsyncCall extends NamedRunnable {
        Callback callback;
        public AsyncCall(Callback callback) {
            this.callback = callback;
        }

        @Override
        protected void execute() {
            // 来这里，开始访问网络 Request -> Response
            Log.e("TAG","execute");
            // Volley xUtils Afinal AsyHttpClient
            // 基于 HttpUrlConnection , OkHttp = Socket + okio(IO)
            final Request request = orignalRequest;
            try {
                List<Interceptor> interceptors = new ArrayList<>();
                interceptors.add(new BridgeInterceptor());
                interceptors.add(new CacheInterceptor());
                interceptors.add(new CallServerInterceptor());
                Interceptor.Chain chain = new RealInterceptorChain(interceptors,0,orignalRequest);
                Response response = chain.proceed(request);
                callback.onResponse(RealCall.this,response);
            } catch (IOException e) {
                callback.onFailure(RealCall.this,e);
            }
        }
    }
}
