package com.east.framelibrary.http;

import android.content.Context;

import com.east.baselibrary.http.EngineCallBack;
import com.east.baselibrary.http.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.Map;

/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 * @description:
 * @author: jamin
 * @date: 2020/5/18
 * |---------------------------------------------------------------------------------------------------------------|
 */
public abstract class HttpCallBack<T> implements EngineCallBack {
    @Override
    public void onPreExecute(Context context, Map<String, Object> params) {
        // 大大方方的添加公用参数  与项目业务逻辑有关
        // 项目名称  context
        params.put("app_name","joke_essay");
        params.put("version_name","5.7.0");
        params.put("ac","wifi");
        params.put("device_id","30036118478");
        params.put("device_brand","Xiaomi");
        params.put("update_version_code","5701");
        params.put("manifest_version_code","570");
        params.put("longitude","113.000366");
        params.put("latitude","28.171377");
        params.put("device_platform","android");
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onSuccess(String result) {
        Gson gson = new Gson();
//        T objResult = gson.fromJson(result, new TypeToken<T>() {
//        }.getType());
        try {
            T objResult = (T) gson.fromJson(result, Utils.analysisClazzInfo(this));
            onSuccess(objResult);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            onError(e);
        }
    }

    public abstract void onSuccess(T result);
}
