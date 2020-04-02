package com.east.architect_zenghui.architect_36_retrofit_optimize.simple7;

/**
 * Created by hcDarren on 2017/12/24.
 */

public interface EngineCallback {
    // 返回可以直接操作的对象,每次在上层自己用字符串去解析会蛋疼
    public abstract void onSuccess(String result);

    public abstract void onFailure(Exception e);
}
