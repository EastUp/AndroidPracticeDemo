package com.east.architect_zenghui.architect_36_retrofit_optimize.simple7;


import com.east.architect_zenghui.architect_36_retrofit_optimize.Utils;

/**
 * Email 240336124@qq.com
 * Created by Darren on 2017/3/5.
 * Version 1.0
 * Description:
 */
public abstract class HttpCallBack<T> implements EngineCallback {
    @Override
    public void onSuccess(String result) {
        // 做解析
        // 不能直接用 Gson 去解析，通过配置去解析
        try {
            Converter converter = HttpUtils.mConfig.converter;
            Class<T> resultT = (Class<T>) Utils.analysisClazzInfo(this);
            T resultObj = converter.convert(result, resultT);
            onSuccess(resultObj);
        }catch (Exception e){
            // 操作异常 ，解析，访问数据正常，但是拿到结果后操作出错
            onFailure(e);
        }
    }

    protected abstract void onSuccess(T resultObj);
}
