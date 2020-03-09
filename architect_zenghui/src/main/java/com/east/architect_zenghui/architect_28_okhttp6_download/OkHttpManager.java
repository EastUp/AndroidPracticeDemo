package com.east.architect_zenghui.architect_28_okhttp6_download;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description:
 *  @author: East
 *  @date: 2020/3/7
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class OkHttpManager {
    private volatile static OkHttpManager mDefaultInstance;
    private OkHttpClient mClient;

    private OkHttpManager() {
        mClient = new OkHttpClient();
    }

    public static OkHttpManager getDefault(){
        if(mDefaultInstance == null){
            synchronized (OkHttpManager.class){
                if(mDefaultInstance == null)
                    mDefaultInstance = new OkHttpManager();
            }
        }
        return mDefaultInstance;
    }

    public Call asyncCall(String url){
        Request request = new Request.Builder().url(url).build();
        return mClient.newCall(request);
    }

    public Response syncResponse(String url, long start, long end) throws IOException {
        Request request = new Request.Builder().url(url)
                .addHeader("Range","bytes="+start+"-"+end).build();
        return mClient.newCall(request).execute();
    }
}
