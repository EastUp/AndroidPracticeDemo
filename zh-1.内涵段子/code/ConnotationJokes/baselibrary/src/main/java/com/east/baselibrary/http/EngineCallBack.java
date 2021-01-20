package com.east.baselibrary.http;

import android.content.Context;

import java.util.Map;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 引擎回调
 *  @author: jamin
 *  @date: 2020/5/18
 * |---------------------------------------------------------------------------------------------------------------|
 */
public interface EngineCallBack {

    /**
     *  开始执行之前调用的方法
     */
    void onPreExecute(Context context, Map<String,Object> params);

    /**
     * 错误
     */
    void onError(Throwable e);

    /**
     * 成功
     */
    void onSuccess(String result);

    EngineCallBack DEFAULT_CALLBACK = new EngineCallBack() {
        @Override
        public void onPreExecute(Context context, Map<String, Object> params) {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onSuccess(String result) {

        }
    };
}
