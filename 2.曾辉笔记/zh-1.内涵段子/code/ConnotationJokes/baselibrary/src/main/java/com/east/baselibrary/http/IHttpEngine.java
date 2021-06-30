package com.east.baselibrary.http;

import android.content.Context;

import java.util.Map;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description:  网络请求引擎
 *  @author: jamin
 *  @date: 2020/5/18
 * |---------------------------------------------------------------------------------------------------------------|
 */
public interface IHttpEngine {

    /**
     * get请求
     */
    void get(boolean cache,Context context, String url, Map<String,Object> params,EngineCallBack callback);


    /**
     * post请求
     */
    void post(boolean cache,Context context, String url, Map<String,Object> params,EngineCallBack callback);
}
