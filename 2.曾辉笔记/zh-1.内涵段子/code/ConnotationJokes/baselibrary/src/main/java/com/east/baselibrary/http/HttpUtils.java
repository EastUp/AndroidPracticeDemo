package com.east.baselibrary.http;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 * @description: 网络请求工具类
 * @author: jamin
 * @date: 2020/5/18
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class HttpUtils {

    private boolean mCache = false; //是否读取缓存

    private String mUrl;  //网络请求地址

    private HttpType mType = HttpType.GET; //默认请求是get

    private Map<String, Object> mParams;

    private Context mContext; // 上下文

    // 默认OkHttpEngine
    private static IHttpEngine mHttpEngine = null;

    // 在Application初始化引擎
    public static void init(IHttpEngine httpEngine){
        mHttpEngine = httpEngine;
    }

    private HttpUtils(Context context) {
        mContext = context;
        mParams = new HashMap<>();
    }

    public static HttpUtils with(Context context) {
        return new HttpUtils(context);
    }

    public HttpUtils url(String url) { //传入url地址
        this.mUrl = url;
        return this;
    }

    public HttpUtils get() { // get请求
        mType = HttpType.GET;
        return this;
    }

    public HttpUtils post() { // post请求
        mType = HttpType.POST;
        return this;
    }

    public HttpUtils cache(boolean cache) { // 是否读取缓存
        mCache = cache;
        return this;
    }

    public HttpUtils addParam(String key,Object value){
        mParams.put(key,value);
        return this;
    }

    public HttpUtils addParams(Map<String,Object> map){
        mParams.putAll(map);
        return this;
    }

    public HttpUtils exechangeEngine(IHttpEngine httpEngine){
        mHttpEngine = httpEngine;
        return this;
    }

    public void execute(){
        execute(null);
    }

    public void execute(EngineCallBack callBack){

        if(callBack == null)
            callBack = EngineCallBack.DEFAULT_CALLBACK;

        callBack.onPreExecute(mContext,mParams);

        if(mType == HttpType.GET)
            get(mUrl,mParams,callBack);
        else if(mType == HttpType.POST)
            post(mUrl,mParams,callBack);
    }

    private void get(String url, Map<String, Object> params, EngineCallBack callBack) {
        mHttpEngine.get(mCache,mContext,url,params,callBack);
    }

    private void post(String url, Map<String, Object> params, EngineCallBack callBack) {
        mHttpEngine.post(mCache,mContext,url,params,callBack);
    }


}
